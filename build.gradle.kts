plugins {
    id("org.jetbrains.intellij") version "1.1.4"
    java
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.serialization") version "1.5.21"
}

group = "work.ezura"
version = "0.9"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2021.1.3")
}
tasks {
    patchPluginXml {
        changeNotes.set(
            """
            Add a feature to translate selected text. (ko -> ja)
            """
            .trimIndent())
    }
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
