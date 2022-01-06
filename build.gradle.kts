// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath("org.jetbrains.kotlin:kotlin-allopen:${Versions.kotlin}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean"){
    delete("build")
}