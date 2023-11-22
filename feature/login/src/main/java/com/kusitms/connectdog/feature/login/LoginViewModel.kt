package com.kusitms.connectdog.feature.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kusitms.connectdog.core.data.api.model.LoginResponseItem
import com.kusitms.connectdog.core.data.api.model.NormalLoginBody
import com.kusitms.connectdog.core.data.api.model.SocialLoginBody
import com.kusitms.connectdog.core.data.repository.LoginRepository
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Provider {
    KAKAO, NAVER
}

private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _volunteerLoginSuccess = MutableLiveData<LoginResponseItem?>()
    val volunteerLoginSuccess: LiveData<LoginResponseItem?> = _volunteerLoginSuccess

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> = _loginError

    fun normalLogin(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = loginRepository.postLoginData(NormalLoginBody(email, password))
                _volunteerLoginSuccess.postValue(response)

                Log.d(TAG, "login success")
            } catch (e: Exception) {
                _loginError.postValue(e.message ?: "로그인 실패")
                Log.d(TAG, e.message.toString())
            }
        }
    }

    private fun postSocialLoginData(provider: Provider, accessToken: String) {
        viewModelScope.launch {
            try {
                val response = loginRepository.postSocialLoginData(
                    SocialLoginBody(
                        accessToken = accessToken,
                        provider = provider.toString()
                    )
                )
                _volunteerLoginSuccess.postValue(response)
            } catch (e: Exception) {
                _loginError.postValue(e.message ?: "로그인 실패")
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun initNaverLogin(context: Context) {
        naverLogin(context)
    }

    fun initKakaoLogin(context: Context) {
        kakaoLogin(context)
    }

    private fun naverLogin(activity: Context) {
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

                            postSocialLoginData(provider = Provider.NAVER, accessToken = NaverIdLoginSDK.getAccessToken().toString())
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

    private fun kakaoLogin(context: Context) {
        KakaoSdk.init(context, "227799f94d3661c7d84b79daba3b0eaa")

        val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("Kakao Login", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("Kakao Login", "카카오계정으로 로그인 성공 ${token.accessToken}")
                postSocialLoginData(provider = Provider.KAKAO, accessToken = token.accessToken)
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e("Kakao Login", "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                    }
                }
            }
        }

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
