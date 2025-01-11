plugins {
    kotlin("jvm") version "2.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

allprojects{
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories{
        mavenCentral()
        mavenLocal()
        maven("https://jitpack.io")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://oss.sonatype.org/content/repositories/central")
    }

    dependencies{
        implementation(kotlin("stdlib"))

        implementation("com.google.code.gson:gson:2.9.0")
        //compileOnly("org.spigotmc:spigot:1.20.6-R0.1-SNAPSHOT")
        compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")

        implementation("im.kimcore:Josa.kt:1.6")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }
}