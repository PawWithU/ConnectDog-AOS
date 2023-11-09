pluginManagement {
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
include(":app")
include(":core:data")
include(":core:model")
include(":core:designsystem")
include(":feature:main")
include(":feature:home")
include(":feature:login")
include(":feature:management")
include(":feature:mypage")
