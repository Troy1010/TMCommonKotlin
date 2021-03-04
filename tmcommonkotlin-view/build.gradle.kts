import com.tminus1010.tmcommonkotlin.Misc

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    `maven-publish`
}

android {
    compileSdkVersion(Misc.targetSDK)
    buildToolsVersion = "30.0.3"

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
    // # Android
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    // # Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.3")
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
                artifactId = "tmcommonkotlin-view"
                version = Misc.versionStr
            }
        }
    }
}