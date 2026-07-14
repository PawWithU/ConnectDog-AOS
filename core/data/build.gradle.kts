import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("connectdog.android.library")
    id("connectdog.android.hilt")
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.kusitms.connectdog.core.data"

    defaultConfig {
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use { localProperties.load(it) }
        }

        val baseUrl: String = localProperties.getProperty("BASE_URL") ?: ""

        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")

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
    implementation(project(":core:model"))
    implementation(project(":core:util"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.bundles.network)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.datastore)

    implementation(libs.naver.oauth)
    implementation(libs.kakao.oauth)
}
