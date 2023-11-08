package com.kusitms.connectdog.core.data.repository

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoginRepository
@Inject
constructor(
    @ApplicationContext private val context: Context
) {
    fun initNaverLogin(activity: Context) {
        NaverIdLoginSDK.initialize(activity, "rWBuAHxm0vihdivNGAZI", "8Sfw75g883", "connectdog")
        NaverIdLoginSDK.authenticate(activity, naverLoginCallback)
    }

    private val naverLoginCallback =
        object : OAuthLoginCallback {
            override fun onSuccess() {
                NidOAuthLogin().callProfileApi(
                    object : NidProfileCallback<NidProfileResponse> {
                        override fun onSuccess(result: NidProfileResponse) {
                            val userName = result.profile!!.name.toString()
                            val userEmail = result.profile!!.email.toString()
                            val userNickname = result.profile!!.nickname.toString()
                            val userImage = result.profile!!.profileImage

                            Log.d("naver login data", NaverIdLoginSDK.getAccessToken().toString())
                            Log.d("naver login data", NaverIdLoginSDK.getExpiresAt().toString())
                            Log.d("naver login data", userName)
                            Log.d("naver login data", userEmail)
                            Log.d("naver login data", userNickname)
                            Log.d("naver login data", userImage.toString())
                        }

                        override fun onError(
                            errorCode: Int,
                            message: String
                        ) {
                            Log.e("login", message)
                        }

                        override fun onFailure(
                            httpStatus: Int,
                            message: String
                        ) {
                            Log.e("login", message)
                        }
                    }
                )
            }

            override fun onError(
                errorCode: Int,
                message: String
            ) {
//            TODO("Not yet implemented")
            }

            override fun onFailure(
                httpStatus: Int,
                message: String
            ) {
//            TODO("Not yet implemented")
            }
        }

    fun initKakaoLogin(context: Context) {
        KakaoSdk.init(context, "227799f94d3661c7d84b79daba3b0eaa")

        val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("Kakao Login", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("Kakao Login", "카카오계정으로 로그인 성공 ${token.accessToken}")
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e("Kakao Login", "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                    }
                }
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e("Kakao Login", "카카오톡으로 로그인 실패", error)

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoLoginCallback)
                } else if (token != null) {
                    Log.i("Kakao Login", "카카오 로그인 성공 ${token.accessToken}")
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e("Kakao Login", "사용자 정보 요청 실패", error)
                        } else if (user != null) {
                        }
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoLoginCallback)
        }
    }
}
