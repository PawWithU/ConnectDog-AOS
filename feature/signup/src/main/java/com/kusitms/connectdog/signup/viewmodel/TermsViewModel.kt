package com.kusitms.connectdog.signup.viewmodel

import androidx.lifecycle.ViewModel
import com.kusitms.connectdog.signup.state.TermsSideEffect
import com.kusitms.connectdog.signup.state.TermsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor() : ContainerHost<TermsUiState, TermsSideEffect>,
    ViewModel() {
    override val container: Container<TermsUiState, TermsSideEffect> =
        container(TermsUiState.empty())
    private val state: TermsUiState
        get() = container.stateFlow.value

    fun onAgreeAllClick() = intent {
        reduce {
            state.copy(
                agreeAll = state.agreeAll.copy(isChecked = !state.agreeAll.isChecked),
                termsOfService = state.termsOfService.copy(isChecked = !state.agreeAll.isChecked),
                privacy = state.privacy.copy(isChecked = !state.agreeAll.isChecked),
                advertisement = state.advertisement.copy(isChecked = !state.agreeAll.isChecked),
            )
        }
        enableNext()
    }

    fun onTermsOfServiceClick() = intent {
        reduce { state.copy(termsOfService = state.termsOfService.copy(isChecked = !state.termsOfService.isChecked)) }
        updateAllAgree()
        enableNext()
    }

    fun onPrivacyClick() = intent {
        reduce { state.copy(privacy = state.privacy.copy(isChecked = !state.privacy.isChecked)) }
        updateAllAgree()
        enableNext()
    }

    fun onAdvertisementClick() = intent {
        reduce { state.copy(advertisement = state.advertisement.copy(isChecked = !state.advertisement.isChecked)) }
        updateAllAgree()
        enableNext()
    }

    private fun updateAllAgree() = intent {
        reduce {
            state.copy(
                agreeAll = state.agreeAll.copy(
                    isChecked = state.termsOfService.isChecked && state.privacy.isChecked && state.advertisement.isChecked
                )
            )
        }
    }

    private fun enableNext() = intent {
        reduce { state.copy(enableNext = state.termsOfService.isChecked && state.privacy.isChecked) }
    }
}
