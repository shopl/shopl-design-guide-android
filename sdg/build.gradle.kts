import com.shopl.sdg.build_logic.PublishingConfig

plugins {
    alias(libs.plugins.sdg.plugin.publishing)
    alias(libs.plugins.sdg.plugin.android.library)
    alias(libs.plugins.sdg.plugin.compose)
    alias(libs.plugins.sdg.plugin.hilt.android)
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

    implementation(libs.joda)
}