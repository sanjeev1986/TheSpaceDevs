plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = Versions.TARGET_SDK
    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.5"
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
    implementation(project(":feature-orchestrator"))
    implementation(Libs.Compose.swipeToRefreshLayout)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.lifecycle_runtime_ktx)
    implementation(Libs.Kotlin.kotlin_stdlib)
    implementation(Libs.core_ktx)
    implementation(Libs.appcompat)
    implementation(Libs.material)
    implementation(Libs.lifecycle_viewmodel_ktx)
    implementation(Libs.lifecycle_runtime_ktx)
    implementation(Libs.Compose.constraintLayout)
    implementation(Libs.Compose.compose_compiler)
    implementation(Libs.Compose.compose_runtime)

    testImplementation(UnitTestLibraries.junit)
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation(UnitTestLibraries.espresso_core)
}