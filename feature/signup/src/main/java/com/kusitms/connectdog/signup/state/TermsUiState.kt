package com.kusitms.connectdog.signup.state

data class CheckBoxDetail(
    val title: String,
    val isChecked: Boolean,
    val hasDetail: Boolean,
    val url: String?,
)

data class TermsUiState(
    val agreeAll: CheckBoxDetail,
    val termsOfService: CheckBoxDetail,
    val privacy: CheckBoxDetail,
    val advertisement: CheckBoxDetail,
    val enableNext: Boolean,
) {
    companion object {
        fun empty() =
            TermsUiState(
                agreeAll =
                    CheckBoxDetail(
                        title = "모두 동의",
                        isChecked = false,
                        hasDetail = false,
                        url = null,
                    ),
                termsOfService =
                    CheckBoxDetail(
                        title = "[필수] 이용약관 동의",
                        isChecked = false,
                        hasDetail = true,
                        url = "https://docs.google.com/document/d/14nF3hzCFfTVGSecKDddO4FJYy27v_c8BUkAU2w_m1O4/edit?usp=sharing",
                    ),
                privacy =
                    CheckBoxDetail(
                        title = "[필수] 개인정보 수집 및 이용 동의",
                        isChecked = false,
                        hasDetail = true,
                        url = "https://docs.google.com/document/d/14nF3hzCFfTVGSecKDddO4FJYy27v_c8BUkAU2w_m1O4/edit?usp=sharing",
                    ),
                advertisement =
                    CheckBoxDetail(
                        title = "[선택] 광고성 정보 수신 동의",
                        isChecked = false,
                        hasDetail = true,
                        url = "https://docs.google.com/document/d/14nF3hzCFfTVGSecKDddO4FJYy27v_c8BUkAU2w_m1O4/edit?usp=sharing",
                    ),
                enableNext = false,
            )
    }
}

sealed class TermsSideEffect
