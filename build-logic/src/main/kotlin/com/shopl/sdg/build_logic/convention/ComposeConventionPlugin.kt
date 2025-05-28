package com.shopl.sdg.build_logic.convention

import com.android.build.gradle.LibraryExtension
import com.shopl.sdg.build_logic.configureAndroidCompose
import com.shopl.sdg.build_logic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Compose 컨벤션 플러그인
 */
class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(plugins) {
                apply(libs.findPlugin("kotlin.compose").get().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                configureAndroidCompose(this)
            }
        }
    }
}