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

rootProject.name = "finmine_project"
include(":app")
include(":feat_auth")
include(":ui_core")
include(":data_core")
include(":feat_notas")
include(":feat_finanzas")
include(":feat_config")
include(":feat_splash")
include(":nav_core")
