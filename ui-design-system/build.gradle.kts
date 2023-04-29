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
                "proguard-rules.pro",
            )
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_version
    }
}

dependencies {
    implementation(Libs.Kotlin.kotlin_stdlib)
    implementation(Libs.appcompat)
    implementation(Libs.material)
    implementation(platform(Libs.Compose.bom))
    implementation(Libs.Compose.material_compose)
    implementation(Libs.Compose.compose_compiler)
    implementation(Libs.Compose.compose_runtime)
    implementation(Libs.Compose.ui_compose)
    implementation(Libs.Compose.viewmodel_compose)
    implementation(Libs.Compose.activity_compose)
    implementation(Libs.Compose.constraintLayout)
}
