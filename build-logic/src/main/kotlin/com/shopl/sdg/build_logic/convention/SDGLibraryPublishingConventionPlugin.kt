package com.shopl.sdg.build_logic.convention

import com.shopl.sdg.build_logic.PublishingConfig.GROUP
import com.shopl.sdg.build_logic.PublishingConfig.VERSION
import com.shopl.sdg.build_logic.libs
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Maven 배포 Plugin Convention
 */
class SDGLibraryPublishingConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin("vanniktech.maven").get().get().pluginId)
        }

        afterEvaluate {
            extensions.configure<MavenPublishBaseExtension> {
                val publishArtifact = (target.findProperty("artifactId") as? String)

                publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
                val isPublishingToLocal = gradle.startParameter.taskNames.any { taskName ->
                    taskName.contains("publishToMavenLocal")
                }
                if (!isPublishingToLocal) {
                    signAllPublications()
                }
                coordinates(
                    GROUP,
                    publishArtifact,
                    VERSION
                )

                pom {
                    name.set("Shopl-Design-Guide")
                    description.set("SDG is a collection of design resources, reusable components and guidelines for creating Shopl's products.")
                    url.set("https://github.com/shopl/shopl-design-guide-android")
                    inceptionYear.set("2025")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("shopl-dev")
                            name.set("shopl-dev")
                            url.set("https://github.com/shopl-dev")
                        }
                    }

                    scm {
                        url.set("https://github.com/shopl/shopl-design-guide-android")
                        connection.set("scm:git:git://github.com/shopl/shopl-design-guide-android.git")
                        developerConnection.set("scm:git:ssh://git@github.com:shopl/shopl-design-guide-android.git")
                    }
                }
            }
        }
    }
}
