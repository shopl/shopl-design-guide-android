package com.shopl.sdg.build_logic.convention

import com.android.build.gradle.LibraryExtension
import com.shopl.sdg.build_logic.configureAndroid
import com.shopl.sdg.build_logic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Android Library 플러그인 컨벤션
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("android.library").get().get().pluginId)
                apply(libs.findPlugin("kotlin.android").get().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                configureAndroid(this)

                defaultConfig.targetSdk = 34
            }
        }
    }
}