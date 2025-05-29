import com.shopl.sdg.build_logic.PublishingConfig

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.sdg.plugin.publishing)
    alias(libs.plugins.sdg.plugin.android.library)
}

extra["artifactId"] = PublishingConfig.SDG_RESOURCE_ARTIFACT_ID

android {
    namespace = "com.shopl.sdg_resource"
}