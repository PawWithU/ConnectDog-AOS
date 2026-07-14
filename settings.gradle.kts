pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://devrepo.kakao.com/nexus/content/groups/public/")
        }
    }
}

rootProject.name = "ConnectDog"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

include(":domain")

include(":core:data")
include(":core:model")
include(":core:designsystem")
include(":core:util")

include(":feature:intermediator")
include(":feature:signup")
include(":feature:main")
include(":feature:home")
include(":feature:login")
include(":feature:management")
include(":feature:mypage")
