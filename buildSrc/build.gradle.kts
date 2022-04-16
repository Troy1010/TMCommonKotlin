// # BuildSrc

plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    google()
    jcenter()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10") // migration to 1.4.31 gave warning: Unsupported Kotlin Version
    implementation(gradleApi())
    implementation(localGroovy())
}