plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
  //  id("com.android.application")

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")

  //  id ("org.jetbrains.kotlin.android")
   // id ("io.realm.kotlin")
}

android {
    namespace = "com.example.go_go_taxy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.go_go_taxy"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    //implementation("com.android.tools:desugar_jdk_libs:1.2.0")  // Make sure this is correct
    //implementation("org.mongodb:mongodb-driver-kotlin-sync:5.2.0")
    implementation ("com.airbnb.android:lottie:6.1.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-auth")

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")


    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries

   // implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.2.1")

  //  implementation("org.mongodb:bson-kotlinx:5.2.1")
//
//implementation ("io.realm.kotlin:library-base:1.16.0")
//    // If using Device Sync
//    implementation ("io.realm.kotlin:library-sync:1.16.0")
//    // If using coroutines with the SDK
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
}


