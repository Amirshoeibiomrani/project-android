pluginManagement {
    repositories {
        maven("https://maven.myket.ir")
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://maven.myket.ir")
        google()
        mavenCentral()
    }
}

rootProject.name = "My Application"
include(":app")
