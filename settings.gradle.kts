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
    }
}

rootProject.name = "ConnectDog"
include(":app")
include(":core:data")
include(":core:model")
include(":core:designsystem")
include(":feature:main")
include(":feature:home")
include(":feature:management")
