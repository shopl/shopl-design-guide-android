import com.shopl.sdg.build_logic.PublishingConfig

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.sdg.plugin.publishing)
    alias(libs.plugins.sdg.plugin.android.library)
    alias(libs.plugins.sdg.plugin.compose)
}

extra["artifactId"] = PublishingConfig.SDG_ARTIFACT_ID

android {
    namespace = "com.shopl.sdg"
}

dependencies {
    // Public
    api(project(":sdg-resource"))
    api(project(":sdg-common"))

    implementation(libs.androidx.lifecycle.viewModelCompose)

    implementation(libs.kotlinx.immutable)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.joda)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    debugImplementation(libs.androidx.ui.tooling)
}