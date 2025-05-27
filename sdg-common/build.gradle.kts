import com.shopl.sdg.build_logic.PublishingConfig

plugins {
    alias(libs.plugins.sdg.plugin.publishing)
    alias(libs.plugins.sdg.plugin.android.library)
    alias(libs.plugins.sdg.plugin.compose)
}

extra["artifactId"] = PublishingConfig.SDG_COMMON_ARTIFACT_ID

android {
    namespace = "com.shopl.sdg_common"
}

dependencies {
    implementation(project(":sdg-resource"))

    implementation(libs.coil.compose)

    implementation(libs.joda)

    debugImplementation(libs.androidx.ui.tooling)
}