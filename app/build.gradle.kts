plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.filmvedizikasifi"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.filmvedizikasifi"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }




    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)




    implementation("com.google.dagger:hilt-android:2.58") //Dependency Injection
    kapt("com.google.dagger:hilt-compiler:2.58") //Dependency Injection
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0") //Dependency Injection

    implementation("com.squareup.retrofit2:retrofit:2.11.0") //Ağ İstekleri
    implementation("com.squareup.retrofit2:converter-gson:2.11.0") //Ağ İstekleri

    implementation("io.coil-kt:coil-compose:2.6.0") //Film afişlerini internetten çekmek için

    implementation("androidx.navigation:navigation-compose:2.8.5") //Sayfalar arası geçiş
}

kotlin {
    jvmToolchain(17)
}