plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
<<<<<<< HEAD
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
=======
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.ksp)
    id("com.google.dagger.hilt.android")
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
}

android {
    namespace = "com.example.lyraapp"
<<<<<<< HEAD
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.lyraapp"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
=======
    compileSdk = 35

    defaultConfig {
        applicationId = "com.turkcell.lyraapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
    buildFeatures {
        compose = true
    }
}

dependencies {
<<<<<<< HEAD
=======
    // Temel Compose ve Yaşam Döngüsü
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
<<<<<<< HEAD
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.media3.common.ktx)
    ksp(libs.hilt.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.androidx.compose.material.icons.extended)
=======
    implementation(libs.androidx.compose.material3)
    implementation("androidx.compose.material:material-icons-extended:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

    // Arayüz Tasarımı ve Preview Desteği (Hataların çözümü burada)
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Navigasyon
    implementation(libs.androidx.navigation.compose)
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
}