pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Ticket Master DotO"
include(
    ":app",

    // Data modules
    ":api-events",
    ":repo-favourites",

    // Domain modules
    ":domain-events",
    ":domain-favourites",

    // Feature modules
    ":feature-events",

    // Library modules
     ":lib-build-config"
)

// Data modules
project(":api-events").projectDir = file("data/api-events")
project(":repo-favourites").projectDir = file("data/repo-favourites")

// Domain modules
project(":domain-events").projectDir = file("domains/domain-events")
project(":domain-favourites").projectDir = file("domains/domain-favourites")

// Feature modules
project(":feature-events").projectDir = file("features/feature-events")

// Library modules
project(":lib-build-config").projectDir = file("libraries/lib-build-config")
