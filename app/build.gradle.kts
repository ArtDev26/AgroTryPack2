

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.agrokasa.agrotrypackv2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.agrokasa.agrotrypackv2"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding{
        enable = true
    }

    packaging {
        resources {
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE.txt"
            merges += "META-INF/NOTICE.md"
            merges += "META-INF/NOTICE.txt"
        }

    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation (libs.zxing.android.embedded)
    implementation (libs.androidx.constraintlayout.v204)
    implementation (libs.material.v130)
    implementation (libs.squareup.retrofit)
    implementation (libs.retrofit2.converter.gson)
    implementation (libs.androidx.appcompat.v120)
    implementation (libs.kotlin.stdlib)
    implementation(libs.okhttp)

    implementation(libs.androidx.databinding.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}