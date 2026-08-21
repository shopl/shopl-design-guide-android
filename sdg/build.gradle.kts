plugins {
    alias(libs.plugins.sdg.plugin.android.library)
    alias(libs.plugins.sdg.plugin.compose)
    alias(libs.plugins.sdg.plugin.hilt.android)
    alias(libs.plugins.sdg.plugin.publishing)
}

android {
    namespace = "com.shopl.sdg"
}

dependencies {
    api(project(":sdg-resource"))
    api(project(":sdg-common"))

    implementation(libs.androidx.lifecycle.viewModelCompose)

    implementation(libs.joda)
}