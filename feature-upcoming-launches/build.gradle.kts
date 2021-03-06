
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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
        kotlinCompilerExtensionVersion = Versions.compose_version
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

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":base"))
    implementation(Libs.Hilt.hilt_android)
    kapt(Libs.Hilt.hilt_compiler)
    implementation(Libs.Navigation.compose)
    implementation(Libs.Compose.constraintLayout)
    implementation(Libs.Maps.maps_ktx)
    implementation(Libs.play_services_maps)
    implementation(Libs.Compose.swipeToRefreshLayout)
    testImplementation(UnitTestLibraries.junit)

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation(UnitTestLibraries.espresso_core)
}