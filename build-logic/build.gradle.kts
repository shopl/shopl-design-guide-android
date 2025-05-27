plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    alias(libs.plugins.vanniktech.maven)
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.maven.publish.gradlePlugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("SDGLibraryPublishingConventionPlugin") {
            id = "sdg.plugin.publishing"
            implementationClass = "com.shopl.sdg.build_logic.convention.SDGLibraryPublishingConventionPlugin"
        }

        register("AndroidLibraryConventionPlugin") {
            id = "sdg.plugin.android.library"
            implementationClass = "com.shopl.sdg.build_logic.convention.AndroidLibraryConventionPlugin"
        }

        register("ComposeConventionPlugin") {
            id = "sdg.plugin.compose"
            implementationClass = "com.shopl.sdg.build_logic.convention.ComposeConventionPlugin"
        }
    }
}