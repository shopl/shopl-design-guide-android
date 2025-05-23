import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.sdg.plugin.library.publishing)
}

group = "io.github.shopl-dev"
version = "0.0.6"

android {
    namespace = "com.shopl.sdg"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Public
    api(project(":sdg-resource"))
    api(project(":sdg-common"))

    implementation(libs.androidx.material3)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.compose.runtime)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.viewModelCompose)

    implementation(libs.kotlinx.immutable)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.joda)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    debugImplementation(libs.androidx.ui.tooling)
}

//mavenPublishing {
//    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
//
//    signAllPublications()
//
//    coordinates("io.github.shopl-dev", "SDG-Android", "$version")
//
//    pom {
//        name = "Shopl-Design-Guide"
//        description = "SDG is a collection of design resources, reusable components and guidelines for creating Shopl's products."
//        url = "https://github.com/shopl/shopl-design-guide-android"
//        inceptionYear = "2025"
//
//        licenses {
//            license {
//                name = "The Apache License, Version 2.0"
//                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
//            }
//        }
//        developers {
//            developer {
//                id = "shopl-dev"
//                name = "shopl-dev"
//                url = "https://github.com/shopl-dev"
//            }
//        }
//
//        scm {
//            url.set("https://github.com/shopl/shopl-design-guide-android")
//            connection.set("scm:git:git://github.com/shopl/shopl-design-guide-android.git")
//            developerConnection.set("scm:git:ssh://git@github.com:shopl/shopl-design-guide-android.git")
//        }
//    }
//}