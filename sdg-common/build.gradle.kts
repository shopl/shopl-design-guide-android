plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.sdg.plugin.library.publishing)
}

extra["artifactId"] = "SDG-Android-Common"
group = "io.github.shopl-dev"
version = "0.0.20"

//1. 배포 테스트하기.
//        2. nowInAndroid 처럼 plugin 클릭하면 따라갈 수 있게 하기.
//                3. 다른 라이브러리 및 android config들도 Minsdk 같은 거 따라가게 하기.
//                        4. gradle properties 내 값들 캡슐화 시키기
//
//                                Note that this key cannot be used for encryption.  You may want to use
//the command "--edit-key" to generate a subkey for this purpose.
//pub   rsa3072 2025-05-23 [SC]
//78565D87506CFBCEFE1657D1CD4C6D4727B675AD
//        uid                      shopl (dev) <dev@shoplworks.com>
//
//        27B675AD
//
//        로컬 배포
//        ./gradlew publishToMavenLocal --no-configuration-cache
//정식 배포
//        ./gradlew publishAndReleaseToMavenCentral --no-configuration-cache
//https://cocoslime.github.io/blog/Android-Library-Maven-Central/
//https://velog.io/@kej_ad/AndroidLibrary%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%ACSDK-Maven-Central%EC%97%90-%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0-2024-08-%EC%B5%9C%EC%8B%A0#%EB%B9%8C%EB%93%9C-%EC%84%A4%EC%A0%95-buildgradlekts
android {
    namespace = "com.shopl.sdg_common"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

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
    implementation(project(":sdg-resource"))

    implementation(libs.androidx.material3)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.compose.runtime)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.coil.compose)

    implementation(libs.joda)

    debugImplementation(libs.androidx.ui.tooling)
}