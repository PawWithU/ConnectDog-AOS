import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("connectdog.android.library")
    id("connectdog.android.compose")
    id("connectdog.android.hilt")
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.kusitms.connectdog.feature.login"

    defaultConfig {
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use { localProperties.load(it) }
        }

        val naverClientId: String = localProperties.getProperty("NAVER_CLIENT_ID") ?: ""
        val naverClientSecret: String = localProperties.getProperty("NAVER_CLIENT_SECRET") ?: ""

        buildConfigField("String", "NAVER_CLIENT_ID", "\"$naverClientId\"")
        buildConfigField("String", "NAVER_CLIENT_SECRET", "\"$naverClientSecret\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.domain)
    implementation(projects.core.model)
    implementation(projects.core.designsystem)
    implementation(projects.core.util)
    implementation(projects.core.data)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.orbit.core)
    implementation(libs.orbit.compose)
    implementation(libs.orbit.viewmodel)

    implementation(libs.androidx.compose.navigation)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.runtime.livedata)

    implementation(libs.kotlinx.collection.imuutable)

    implementation(libs.kakao.oauth)
    implementation(libs.naver.oauth)
}
