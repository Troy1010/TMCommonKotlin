import com.tminus1010.tmcommonkotlin.Misc

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    `maven-publish`
}

android {
    compileSdkVersion(Misc.compileSDK)
    buildToolsVersion = "30.0.2"

    compileOptions.sourceCompatibility = JavaVersion.VERSION_1_8
    compileOptions.targetCompatibility = JavaVersion.VERSION_1_8
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()

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
    implementation("io.reactivex.rxjava3:rxjava:3.0.7")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    // ## Rx + Lifecycle
    implementation("com.trello.rxlifecycle4:rxlifecycle-android:4.0.0")
    implementation("com.trello.rxlifecycle4:rxlifecycle-components:4.0.0")
    implementation("com.trello.rxlifecycle4:rxlifecycle-components-preference:4.0.0")
    implementation("com.trello.rxlifecycle4:rxlifecycle-kotlin:4.0.0")
    implementation("com.trello.rxlifecycle4:rxlifecycle-android-lifecycle-kotlin:4.0.0")
    // # This Project
    implementation(project(":tmcommonkotlin-tuple"))
    implementation(project(":tmcommonkotlin-logz"))
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