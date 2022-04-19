// # BuildSrc

plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10") // migration to 1.4.31 gave warning: Unsupported Kotlin Version
    implementation(gradleApi())
    implementation(localGroovy())
}