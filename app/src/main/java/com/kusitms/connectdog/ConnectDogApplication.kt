package com.kusitms.connectdog

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ConnectDogApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "227799f94d3661c7d84b79daba3b0eaa")
    }
}
