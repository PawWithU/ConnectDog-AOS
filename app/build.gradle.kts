import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("connectdog.android.application")
    id("connectdog.android.hilt")
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.kusitms.connectdog"

    defaultConfig {
        applicationId = "com.kusitms.connectdog"
        targetSdk = 34
        versionCode = 15
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use { localProperties.load(it) }
        }

        val kakaoAppKey: String = localProperties.getProperty("kakao_app_key") ?: ""
        manifestPlaceholders["KAKAO_APP_KEY"] = kakaoAppKey
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:main"))
    implementation(project(":feature:intermediator"))

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.google.playintegrity)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.kakao.oauth)
}
