plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = Versions.TARGET_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(Libs.Kotlin.kotlinx_coroutines_android)
    implementation(Libs.Kotlin.kotlin_coroutines)
    testImplementation(Libs.JUNIT)

    api(project(":data-web-services"))
    implementation(project(":android-platform"))
    implementation(Libs.Dagger.dagger)
    implementation(Libs.Kotlin.kotlin_stdlib)
    implementation(Libs.core_ktx)
    androidTestImplementation("androidx.test.ext:junit:1.1.2")

    androidTestImplementation(UnitTestLibraries.espresso_core)
    testImplementation(UnitTestLibraries.junit)
}