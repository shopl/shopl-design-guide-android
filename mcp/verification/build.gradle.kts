plugins {
    id("com.android.library") version "8.10.0"
    id("org.jetbrains.kotlin.android") version "2.1.20"
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20"
}
val sdgVersion = providers.gradleProperty("sdgVersion").get()
layout.buildDirectory.set(file(providers.gradleProperty("sdgBuildDir").get()))

android {
    namespace = "com.shopl.sdg.mcp.examples"
    compileSdk = 36
    defaultConfig { minSdk = 28 }
    buildFeatures { compose = true }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions { jvmTarget = "11" }
    sourceSets["main"].java.srcDir(providers.gradleProperty("sdgProbeDir").get())
}
dependencies {
    implementation("com.shoplworks:SDG-Android:$sdgVersion")
    implementation("com.shoplworks:SDG-Android-Common:$sdgVersion")
    implementation("com.shoplworks:SDG-Android-Resource:$sdgVersion")
    implementation(platform("androidx.compose:compose-bom:2025.05.01"))
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")
    implementation("net.danlew:android.joda:2.12.5")
}
