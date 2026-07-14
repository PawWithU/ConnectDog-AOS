@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("connectdog.android.library")
    id("connectdog.android.compose")
    id("connectdog.android.hilt")
    alias(libs.plugins.ktlint)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.kusitms.connectdog.feature.main"

    defaultConfig {
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
}

dependencies {
    implementation(projects.domain)
    // Navigation을 위해 모든 feature 의존성 추가 필요
    implementation(project(":feature:login"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:home"))
    implementation(project(":feature:intermediator"))
    implementation(project(":feature:management"))
    implementation(project(":feature:mypage"))

    implementation(project(":core:model"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:util"))
    implementation(project(":core:data"))

    implementation(libs.androidx.core.splashscreen)
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation(libs.google.playintegrity)

    implementation(libs.google.gson)

    implementation(libs.androidx.compose.navigation)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.firebase.bom)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.messaging)
    implementation(libs.google.service)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.activity.compose)

    implementation(libs.kotlinx.collection.imuutable)
    implementation(libs.orbit.core)
    implementation(libs.orbit.compose)
    implementation(libs.orbit.viewmodel)
}
