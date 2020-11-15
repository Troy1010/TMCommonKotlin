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
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.61")
    //
    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.2")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7")
    // Rx
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation("io.reactivex.rxjava3:rxjava:3.0.4")
    // Rx+LiveData
    implementation("androidx.lifecycle:lifecycle-reactivestreams:2.2.0")
    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")
    // JUnit
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    // Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
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
                artifactId = "tmcommonkotlin-tuple"
                version = Misc.versionStr
            }
        }
    }
}