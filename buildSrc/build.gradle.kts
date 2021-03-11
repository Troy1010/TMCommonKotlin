plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    google()
    jcenter()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.1.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
    implementation("com.tminus1010.tmcommonkotlin:tmcommonkotlindsl:+")
    implementation(gradleApi())
    implementation(localGroovy())
}