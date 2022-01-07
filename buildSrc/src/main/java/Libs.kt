object Libs {
    const val KOTLIN_STD_LIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val DAGGER = "com.google.dagger:dagger:${Versions.dagger}"
    const val JUNIT = "junit:junit:${Versions.JUNIT}"

    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlin_android_couroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val kotlin_couroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.corektx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val activity_ktx = "androidx.activity:activity-ktx:${Versions.activity}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val navigation_ui_ktx =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigation_components}"
    const val navigation_fragment_ktx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation_components}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val dagger_android_processor =
        "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val swiperefreshlayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
    const val kotlinx_coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinx_coroutines_android}"
    const val lifecycle_viewmodel_ktx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.android_architecture_components}"
    const val lifecycle_runtime_ktx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.android_architecture_components}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val play_services_maps =
        "com.google.android.gms:play-services-maps:${Versions.play_services_maps}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.gson}"

    const val activity_compose = "androidx.activity:activity-compose:1.3.1"
    const val viewmodel_compose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"

    const val material_compose = "androidx.compose.material:material:${Versions.compose_version}"
    const val animation_compose = "androidx.compose.animation:animation:${Versions.compose_version}"
    const val ui_compose = "androidx.compose.ui:ui-tooling:${Versions.compose_version}"
    const val mdc_compose =
        "com.google.android.material:compose-theme-adapter:${Versions.compose_version}"
    const val appcompat_compose = "com.google.accompanist:accompanist-appcompat-theme:0.16.0"
    const val compose_runtime = "androidx.compose.runtime:runtime:${Versions.compose_version}"
    const val compose_compiler = "androidx.compose.compiler:compiler:${Versions.compose_version}"
    const val compose_livedata =
        "androidx.compose.runtime:runtime-livedata:${Versions.compose_version}"
    const val livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
}

object UnitTestLibraries {
    const val junit = "junit:junit:${Versions.junit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espresso_contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val core = "androidx.test:core:${Versions.test_core}"
    const val core_ktx = "androidx.test:core-ktx:${Versions.test_core}"
    const val junit_ktx = "androidx.test.ext:junit-ktx:${Versions.junit_ktx}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockk_android = "io.mockk:mockk-android:${Versions.mockk_android}"
    const val core_testing = "androidx.arch.core:core-testing:${Versions.core_testing}"
    const val core_runtime = "androidx.arch.core:core-runtime:${Versions.core_testing}"
    const val espresso_idling_resource =
        "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"
    const val ui_test_compose = "androidx.compose.ui:ui-test-junit4:1.0.5"
}