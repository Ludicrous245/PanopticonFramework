import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.0"

    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("idea")
}

group = "org.example"
version = "1.0-SNAPSHOT"

project.extra.set("pluginName", name.split('-').joinToString("") { it.capitalize() })

dependencies{
    api(project(":PanopticonCore"))
    api(project(":PanopticonEvent"))
    api(project(":PanopticonGUI"))
    api(project(":PanopticonCommand"))
    api(fileTree("src/main/libs") { include("*.jar") })
}

tasks {
    named<ShadowJar>("shadowJar") {
        dependencies{
            exclude("org.spigotmc:spigot:1.20.4-R0.1-SNAPSHOT")
        }
        archiveBaseName.set("PanopticonTestPlugin")

        mergeServiceFiles()
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}