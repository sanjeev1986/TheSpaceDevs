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
    api(project(":data-repositories"))
    api(project(":ui-design-system"))
    api(project(":data-web-services"))

    api(Libs.kotlin_stdlib)
    api(Libs.core_ktx)
    api(Libs.appcompat)
    api(Libs.material)
    api(Libs.constraintlayout)

    api(Libs.activity_ktx)
    api(Libs.fragment_ktx)

    api(Libs.navigation_ui_ktx)
    api(Libs.navigation_fragment_ktx)

    api(Libs.dagger)
    kapt(Libs.dagger_compiler)
    kapt(Libs.dagger_android_processor)
    kaptAndroidTest(Libs.dagger_compiler)
    api(Libs.dagger_android_support)


    api(Libs.swiperefreshlayout)
    api(Libs.kotlin_couroutines)

    //UI testing
    androidTestApi(UnitTestLibraries.junit)
    androidTestApi(UnitTestLibraries.espresso_core)
    androidTestApi(UnitTestLibraries.espresso_contrib)
    androidTestApi(UnitTestLibraries.core)
    androidTestApi(UnitTestLibraries.core_ktx)
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestApi(UnitTestLibraries.junit_ktx)
    androidTestApi(UnitTestLibraries.mockk_android)
    androidTestApi(UnitTestLibraries.core_testing)
    androidTestApi(UnitTestLibraries.core_runtime)
    api(UnitTestLibraries.espresso_idling_resource)
    api(Libs.gson)
    api("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")
}