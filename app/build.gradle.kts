plugins {
    alias(libs.plugins.android.application)
    
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ila"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ila"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:34.2.0"))

    // Firebase Analytics (required for all Firebase products)
    implementation("com.google.firebase:firebase-analytics")
    
    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth")
    
    // Firebase Firestore Database
    implementation("com.google.firebase:firebase-firestore")
    
    // Firebase Cloud Storage
    implementation("com.google.firebase:firebase-storage")
    
    // Firebase Cloud Messaging (Push Notifications)
    implementation("com.google.firebase:firebase-messaging")
    

    

    
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}