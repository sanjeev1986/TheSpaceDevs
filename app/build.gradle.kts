plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Versions.TARGET_SDK

    defaultConfig {
        applicationId = "com.sample.thespacedevs"
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "com.sample.thespacedevs.DaggerTestRunner"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../thespacedevscredentials")
            keyAlias = "spacedevsapp"
            keyPassword = "TheSpaceDevs@2021"
            storePassword = "TheSpaceDevs@2021"
        }

        create("release") {
            storeFile = file("../thespacedevscredentials")
            keyAlias = "spacedevsapp"
            keyPassword = "TheSpaceDevs@2021"
            storePassword = "TheSpaceDevs@2021"
        }
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

    testOptions {
        animationsDisabled = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.5"
    }
}

dependencies {
    implementation(project(":feature-orchestrator"))
    implementation(project(":feature-upcoming-launches"))
    implementation(Libs.play_services_maps)
    implementation(project(":feature-vehicles"))
    runtimeOnly(Libs.play_services_maps)
    implementation(Libs.Compose.compose_compiler)
    implementation(Libs.Compose.compose_runtime)

    //unit testing
    testImplementation(UnitTestLibraries.junit)
    testImplementation(UnitTestLibraries.mockk)
    debugImplementation("androidx.fragment:fragment-testing:1.3.2") {
        exclude("androidx.test", "core")
    }
    implementation(Libs.Hilt.hilt_android)
    kapt(Libs.Hilt.hilt_compiler)
}

kapt {
    correctErrorTypes = true
}