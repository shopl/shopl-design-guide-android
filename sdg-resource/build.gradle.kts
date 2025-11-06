plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.sdg.plugin.android.library)
    alias(libs.plugins.sdg.plugin.publishing)
}

android {
    namespace = "com.shopl.sdg_resource"
}