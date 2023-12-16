import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.21"

    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("idea")
}

group = "org.example"
version = "1.0-SNAPSHOT"

project.extra.set("pluginName", name.split('-').joinToString("") { it.capitalize() })

dependencies{
    api(project(":PanopticonCore"))
    api(project(":PanopticonGUI"))
    api(project(":PanopticonEvent"))
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("PanopticonTestPlugin")

        mergeServiceFiles()
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}