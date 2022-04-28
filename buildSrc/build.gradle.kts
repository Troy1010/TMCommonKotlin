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
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
    implementation(gradleApi())
    implementation(localGroovy())
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.41")
}