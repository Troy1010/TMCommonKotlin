import com.tminus1010.tmcommonkotlin.Misc

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    `maven-publish`
}

android {
    compileSdkVersion(Misc.targetSDK)
    buildToolsVersion = "30.0.2"

    compileOptions.sourceCompatibility = JavaVersion.VERSION_1_8
    compileOptions.targetCompatibility = JavaVersion.VERSION_1_8
    kotlinOptions.jvmTarget = "1.8"

    defaultConfig {
        minSdkVersion(Misc.minSDK)
        targetSdkVersion(Misc.targetSDK)
        versionCode = 1
        versionName = Misc.versionStr

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    // # Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.20")
    // # JUnit
    testImplementation("junit:junit:4.13.2")
    // # Rx
    api("io.reactivex.rxjava3:rxjava:3.0.7")
    api("io.reactivex.rxjava3:rxandroid:3.0.0")
    // ## Rx + Lifecycle
    api("com.trello.rxlifecycle4:rxlifecycle-android-lifecycle-kotlin:4.0.0")
    // # This Project
    api(project(":tmcommonkotlin-tuple"))
}


tasks.register("sourceJar", Jar::class) {
    from("src/main")
}

afterEvaluate {
    publishing {
        publications {
            register("mavenPublication", MavenPublication::class) {
                artifact("$buildDir/outputs/aar/${artifactId}-release.aar")
                artifact(tasks.getByName("sourceJar")) { classifier = "sources" }
                groupId = Misc.groupId
                artifactId = "tmcommonkotlin-rx"
                version = Misc.versionStr
            }
        }
    }
}