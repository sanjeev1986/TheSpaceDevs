plugins {
    id("kotlin")
    id("com.android.lint")
}

dependencies {
    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.logging_interceptor)
    implementation(Libs.Retrofit.converter_gson)
}