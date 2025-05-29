package com.shopl.sdg.build_logic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * 안드로이드 구성 세팅
 */
internal fun Project.configureAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 35

        defaultConfig {
            minSdk = 28

            testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
            vectorDrawables.useSupportLibrary = true
        }
        packaging {
            resources {
                excludes.add("META-INF/**")
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
                freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
            }
        }
    }
}