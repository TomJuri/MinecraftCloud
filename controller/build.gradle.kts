plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

val embed: Configuration by configurations.creating
configurations.implementation.get().extendsFrom(embed)

dependencies {
    embed(project(":api"))
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    embed("com.google.code.gson:gson:2.10.1")
    embed("org.jline:jline:3.24.1")
    embed("io.netty:netty5-all:5.0.0.Alpha5")
    embed("org.fusesource.jansi:jansi:2.4.1")
    embed("cloud.commandframework:cloud-core:1.8.4")
    embed("cloud.commandframework:cloud-annotations:1.8.4")
    embed("com.google.dagger:dagger:2.48.1")
    annotationProcessor("com.google.dagger:dagger-compiler:2.48.1")
    annotationProcessor("cloud.commandframework:cloud-annotations:1.8.4")
}

tasks.build.get().dependsOn(tasks.shadowJar)

tasks.jar {
    manifest.attributes["Main-Class"] = "de.tomjuri.hycloud.controller.Launcher"
}

tasks.shadowJar {
    archiveClassifier.set("")
    configurations = listOf(embed)
}