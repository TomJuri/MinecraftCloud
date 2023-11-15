plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    shadow(project(":api"))
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    shadow("com.google.code.gson:gson:2.10.1")
    shadow("org.jline:jline:3.24.1")
    shadow("io.netty:netty5-all:5.0.0.Alpha5")
    shadow("org.fusesource.jansi:jansi:2.4.1")
}

tasks.build.get().dependsOn(tasks.shadowJar)

tasks.jar {
    manifest.attributes.set("Main-Class", "de.tomjuri.hycloud.wrapper.Launcher")
}

tasks.shadowJar {
    archiveClassifier.set("")
    configurations = listOf(project.configurations.shadow.get())
    isEnableRelocation = true
    relocationPrefix = "de.tomjuri.hycloud.relocate"
}