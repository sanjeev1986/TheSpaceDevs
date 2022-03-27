plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-allopen")
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
    allOpen {
        annotation("OpenClass")
    }
}

dependencies {
    api(project(":android-platform"))
    api(project(":ui-design-system"))
    api(project(":data-web-services"))
    api(Libs.lifecycle_viewmodel_ktx)
    api(Libs.lifecycle_runtime_ktx)
    api(Libs.Compose.compose_compiler)
    api(Libs.Compose.compose_runtime)
    api(Libs.Compose.activity_compose)
    api(Libs.Compose.ui_compose)
    api(Libs.Compose.material_compose)
    api(Libs.livedata_ktx)
    api(Libs.Compose.compose_livedata)
    api(Libs.Compose.coil)

    api(Libs.Kotlin.kotlin_stdlib)
    api(Libs.core_ktx)
    api(Libs.appcompat)
    api(Libs.material)
    api(Libs.constraintlayout)

    api(Libs.activity_ktx)
    api(Libs.fragment_ktx)

    api(Libs.Navigation.ui_ktx)
    api(Libs.Navigation.fragment_ktx)
    api(Libs.Navigation.compose)


    api(Libs.Kotlin.kotlin_coroutines)
    api(Libs.Kotlin.kotlinx_coroutines_android)

    //UI testing
    androidTestApi(UnitTestLibraries.junit)
    androidTestApi(UnitTestLibraries.espresso_core)
    androidTestApi(UnitTestLibraries.espresso_contrib)
    androidTestApi(UnitTestLibraries.core)
    androidTestApi(UnitTestLibraries.core_ktx)
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestApi(UnitTestLibraries.junit_ktx)
    androidTestApi(UnitTestLibraries.mockk_android)
    androidTestApi(UnitTestLibraries.core_testing)
    androidTestApi(UnitTestLibraries.core_runtime)
    api(UnitTestLibraries.espresso_idling_resource)
    api(Libs.Retrofit.gson)
    implementation(Libs.Hilt.hilt_android)
}

