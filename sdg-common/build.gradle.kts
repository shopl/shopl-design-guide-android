plugins {
    alias(libs.plugins.sdg.plugin.android.library)
    alias(libs.plugins.sdg.plugin.compose)
    alias(libs.plugins.sdg.plugin.publishing)
}

android {
    namespace = "com.shopl.sdg_common"
}

dependencies {
    implementation(project(":sdg-resource"))

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    api(libs.kotlinx.immutable)

    implementation(libs.joda)

    debugImplementation(libs.androidx.ui.tooling)
}