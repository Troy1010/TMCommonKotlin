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
    implementation("com.android.tools.build:gradle:8.0.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.21")
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.41")
    implementation("de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1")
}