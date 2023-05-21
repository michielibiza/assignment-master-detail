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

rootProject.name = "Assignment"
include(":app")
include(":feature:repositories-presentation")
include(":feature:repositories-domain")
include(":core:design")
include(":feature:repositories-data")
