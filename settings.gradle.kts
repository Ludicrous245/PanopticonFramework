pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "PanopticonFramework"
include("PanopticonCore")
include("PanopticonEvent")
include("PanopticonTestPlugin")
include("PanopticonGUI")
