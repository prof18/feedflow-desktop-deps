rootProject.name = "feedflow-desktop-deps"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
            content {
                includeModule("com.github.Dansoftowner", "jSystemThemeDetector")
                // TODO: Delete when/if https://github.com/adrielcafe/lyricist/pull/45
                //  and https://github.com/adrielcafe/lyricist/pull/46 gets merged
                includeModule("com.github.prof18", "lyricist")
            }
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":composeApp")