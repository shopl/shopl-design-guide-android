package com.shopl.sdg.build_logic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Compose 구성 세팅
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures.compose = true

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }

    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").get()
        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))

        add("implementation", libs.findLibrary("androidx.material3").get())
        add("implementation", libs.findLibrary("compose.runtime").get())
        add("implementation", libs.findLibrary("androidx.ui").get())
        add("implementation", libs.findLibrary("androidx.ui.graphics").get())
        add("implementation", libs.findLibrary("kotlinx.immutable").get())
        add("implementation", libs.findLibrary("androidx.ui.tooling.preview").get())

        add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
    }
}