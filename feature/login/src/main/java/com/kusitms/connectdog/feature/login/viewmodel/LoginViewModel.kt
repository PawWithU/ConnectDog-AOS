package com.kusitms.connectdog.feature.login.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.domain.usecase.login.AppMode
import com.kusitms.connectdog.domain.usecase.login.IntermediatorLoginUseCase
import com.kusitms.connectdog.domain.usecase.login.SocialLoginProvider
import com.kusitms.connectdog.domain.usecase.login.SocialLoginUseCase
import com.kusitms.connectdog.domain.usecase.login.UpdateAccessTokenUseCase
import com.kusitms.connectdog.domain.usecase.login.UpdateAppModeUseCase
import com.kusitms.connectdog.domain.usecase.login.UpdateRefreshTokenUseCase
import com.kusitms.connectdog.domain.usecase.login.UpdateSocialProviderUseCase
import com.kusitms.connectdog.domain.usecase.login.UpdateSocialTokenUseCase
import com.kusitms.connectdog.domain.usecase.login.VolunteerLoginUseCase
import com.kusitms.connectdog.feature.login.BuildConfig
import com.kusitms.connectdog.feature.login.state.LoginSideEffect
import com.kusitms.connectdog.feature.login.state.LoginUiState
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val volunteerLoginUseCase: VolunteerLoginUseCase,
        private val intermediatorLoginUseCase: IntermediatorLoginUseCase,
        private val socialLoginUseCase: SocialLoginUseCase,
        private val updateAccessTokenUseCase: UpdateAccessTokenUseCase,
        private val updateRefreshTokenUseCase: UpdateRefreshTokenUseCase,
        private val updateAppModeUseCase: UpdateAppModeUseCase,
        private val updateSocialTokenUseCase: UpdateSocialTokenUseCase,
        private val updateSocialProviderUseCase: UpdateSocialProviderUseCase,
    ) : ContainerHost<LoginUiState, LoginSideEffect>, ViewModel() {
        override val container: Container<LoginUiState, LoginSideEffect> = container(LoginUiState.empty())
        private val state: LoginUiState
            get() = container.stateFlow.value

        fun onEmailChanged(email: String) = intent { reduce { state.copy(email = email) } }

        fun onPasswordChanged(password: String) = intent { reduce { state.copy(password = password) } }

        fun initVolunteerLogin() =
            viewModelScope.launch {
                if (state.email.isBlank() || state.password.isBlank()) {
                    intent { postSideEffect(LoginSideEffect.ShowErrorToast("이메일 혹은 비밀번호를 입력해주세요.")) }
                    return@launch
                }

                volunteerLoginUseCase(
                    email = state.email,
                    password = state.password,
                ).onSuccess {
                    updateAccessTokenUseCase(it.accessToken)
                    updateRefreshTokenUseCase(it.refreshToken)
                    updateAppModeUseCase(AppMode.VOLUNTEER)
                    intent {
                        reduce { state.copy(isLoginSuccessful = true) }
                        postSideEffect(LoginSideEffect.NavigateToHome)
                    }
                }.onFailure {
                    intent {
                        reduce { state.copy(isLoginSuccessful = false) }
                        postSideEffect(LoginSideEffect.ShowErrorToast("이메일 혹은 비밀번호가 일치하지 않습니다"))
                    }
                }
            }

        fun initIntermediatorLogin() =
            viewModelScope.launch {
                if (state.email.isBlank() || state.password.isBlank()) {
                    intent { postSideEffect(LoginSideEffect.ShowErrorToast("이메일 혹은 비밀번호를 입력해주세요.")) }
                    return@launch
                }

                intermediatorLoginUseCase(
                    email = state.email,
                    password = state.password,
                ).onSuccess {
                    intent {
                        reduce { state.copy(isLoginSuccessful = true) }
                        postSideEffect(LoginSideEffect.NavigateToHome)
                    }
                    updateAccessTokenUseCase(it.accessToken)
                    updateRefreshTokenUseCase(it.refreshToken)
                    updateAppModeUseCase(AppMode.INTERMEDIATOR)
                }.onFailure {
                    intent {
                        reduce { state.copy(isLoginSuccessful = false) }
                        postSideEffect(LoginSideEffect.ShowErrorToast("이메일 혹은 비밀번호가 일치하지 않습니다"))
                    }
                }
            }

        private fun initSocialLogin(
            provider: SocialLoginProvider,
            token: String,
        ) = viewModelScope.launch {
            socialLoginUseCase(
                accessToken = token,
                provider = provider.toString(),
            ).onSuccess {
                updateAccessTokenUseCase(it.accessToken)
                updateRefreshTokenUseCase(it.refreshToken)
                when (it.roleName) {
                    "GUEST" -> intent { postSideEffect(LoginSideEffect.NavigateToSignUp(UserType.SOCIAL_VOLUNTEER)) }
                    "AUTH_VOLUNTEER" -> {
                        intent { postSideEffect(LoginSideEffect.NavigateToHome) }
                        updateAppModeUseCase(AppMode.VOLUNTEER)
                    }
                }
            }
        }

        private val naverLoginCallback =
            object : OAuthLoginCallback {
                override fun onSuccess() {
                    NidOAuthLogin().callProfileApi(
                        object : NidProfileCallback<NidProfileResponse> {
                            override fun onSuccess(result: NidProfileResponse) {
                                initSocialLogin(SocialLoginProvider.NAVER, NaverIdLoginSDK.getAccessToken().toString())
                            }

                            override fun onError(
                                errorCode: Int,
                                message: String,
                            ) {}

                            override fun onFailure(
                                httpStatus: Int,
                                message: String,
                            ) {}
                        },
                    )
                }

                override fun onError(
                    errorCode: Int,
                    message: String,
                ) {}

                override fun onFailure(
                    httpStatus: Int,
                    message: String,
                ) {}
            }

        private val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (token != null && error != null) {
                initSocialLogin(SocialLoginProvider.KAKAO, token.accessToken)
            }
        }

        fun initNaverLogin(context: Context) {
            NaverIdLoginSDK.initialize(context, BuildConfig.NAVER_CLIENT_ID, BuildConfig.NAVER_CLIENT_SECRET, "connectdog")
            NaverIdLoginSDK.authenticate(context, naverLoginCallback)
        }

        fun initKakaoLogin(context: Context) {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null && token != null) {
                        initSocialLogin(SocialLoginProvider.KAKAO, token.accessToken)
                    } else if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoLoginCallback)
                        return@loginWithKakaoTalk
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoLoginCallback)
            }
        }
    }
