package com.shopl.sdg.build_logic.convention

import com.android.build.gradle.LibraryExtension
import com.shopl.sdg.build_logic.configureAndroid
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
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureAndroid(this)

                defaultConfig.targetSdk = 34
            }
        }
    }
}