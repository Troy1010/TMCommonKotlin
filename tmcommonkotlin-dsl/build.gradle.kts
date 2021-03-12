import com.tminus1010.tmcommonkotlin.Misc

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("maven-publish")
}

// # Define a plugin to build, give it an id
gradlePlugin {
    plugins {
        create("keyForPlugin") {
            id = "TMRootPlugin"
            implementationClass = "com.tminus1010.tmcommonkotlin.dsl.TMRootPlugin"
        }
        create("keyForPlugin2") {
            id = "TMPlugin"
            implementationClass = "com.tminus1010.tmcommonkotlin.dsl.TMPlugin"
        }
    }
}

publishing {
    publications {
        create("pluginPublication", MavenPublication::class) {
            from(project.components["java"])
            groupId = Misc.groupId
            artifactId = "tmcommonkotlin-dsl"
            version = Misc.versionStr
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:4.1.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    implementation(localGroovy())
    implementation(gradleApi())
}