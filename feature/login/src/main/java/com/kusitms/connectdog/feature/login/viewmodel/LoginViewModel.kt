package com.kusitms.connectdog.feature.login.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kusitms.connectdog.core.data.api.model.NormalLoginBody
import com.kusitms.connectdog.core.data.api.model.SocialLoginBody
import com.kusitms.connectdog.core.data.repository.DataStoreRepository
import com.kusitms.connectdog.core.data.repository.LoginRepository
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
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
    private val loginRepository: LoginRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _isLoginSuccessful: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isLoginSuccessful: StateFlow<Boolean?>
        get() = _isLoginSuccessful

    private val _email: MutableState<String> = mutableStateOf("")
    val email: String
        get() = _email.value

    private val _password: MutableState<String> = mutableStateOf("")
    val password: String
        get() = _password.value

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun initVolunteerLogin() {
        viewModelScope.launch {
            try {
                val response = loginRepository.postLoginData(NormalLoginBody(_email.value, _password.value))
                dataStoreRepository.saveAccessToken(response.accessToken)
                _isLoginSuccessful.value = true
                Log.d(TAG, isLoginSuccessful.toString())
                Log.d(TAG, dataStoreRepository.accessTokenFlow.first().toString())
            } catch (e: Exception) {
                _isLoginSuccessful.value = false
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun initIntermediatorLogin() {
        viewModelScope.launch {
            try {
                val response = loginRepository.postIntermediatorLoginData(NormalLoginBody(_email.value, _password.value))

                viewModelScope.launch {
                    dataStoreRepository.saveAccessToken(response.accessToken)
                }
            } catch (e: Exception) {
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
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
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
