plugins {
    id ("com.android.library")
    id ("kotlin-android")
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
    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.5"
    }
}

dependencies {
    implementation (Libs.kotlin_stdlib)
    implementation (Libs.appcompat)
    implementation (Libs.material)
    implementation(Libs.material_compose)
    implementation(Libs.compose_compiler)
    implementation(Libs.compose_runtime)
    implementation(Libs.ui_compose)
    implementation(Libs.viewmodel_compose)
    implementation(Libs.activity_compose)
}