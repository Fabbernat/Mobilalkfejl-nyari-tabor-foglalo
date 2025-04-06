plugins {
//    id("com.android.application")
    alias(libs.plugins.android.application)

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.mobilalkfejl_nyari_tabor_foglalo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mobilalkfejl_nyari_tabor_foglalo"
        minSdk = 16
        targetSdk = 35
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

    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {
    // Import the Firebase BoM
//    implementation(platform("com.google.firebase:firebase-bom:33.12.0"))
    implementation(platform(libs.firebase.bom))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
//    implementation("com.google.firebase:firebase-analytics")
    implementation(libs.google.firebase.analytics)

    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
//    implementation(com.google.firebase:firebase.auth)

    implementation(libs.recyclerview)
    implementation(libs.recyclerview.selection)
    implementation(libs.cardview)
    implementation(libs.glide)
    /*
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
     */
}