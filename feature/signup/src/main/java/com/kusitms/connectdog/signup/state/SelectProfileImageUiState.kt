package com.kusitms.connectdog.signup.state

data class SelectProfileImageUiState(
    val selectedImageId: Int
) {
    companion object {
        fun empty() = SelectProfileImageUiState(
            selectedImageId = 1
        )
    }
}

sealed class SelectProfileImageSideEffect
