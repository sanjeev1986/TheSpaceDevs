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