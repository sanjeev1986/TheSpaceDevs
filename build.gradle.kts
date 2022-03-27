// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Classpaths.gradle)
        classpath(Classpaths.gradleKotlinPlugin)
        classpath(Classpaths.navigationSafeArgsGradle)
        classpath(Classpaths.allOpen)
        classpath(Classpaths.googleMaps)
        classpath(Classpaths.hilt)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean") {
    delete("build")
}