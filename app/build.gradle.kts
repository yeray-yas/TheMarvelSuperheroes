plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

// Epoxy
kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.yeray_yas.marvelsuperheroes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yeray_yas.marvelsuperheroes"
        minSdk = 24
        targetSdk = 33
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    val retrofitVersion = "2.9.0"
    val viewModelVersion = "2.6.2"
    val coroutinesVersion = "1.7.3"
    val epoxyVersion = "4.4.1"
    val chuckerVersion = "3.4.0"
    val navVersion = "2.7.6"

    //Basic dependencies
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Firebase
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")

    // Retrofit2
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$viewModelVersion")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.core:core-ktx:1.12.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // Epoxy
    implementation("com.airbnb.android:epoxy:$epoxyVersion")
    kapt("com.airbnb.android:epoxy-processor:$epoxyVersion")// todo change to ksp
    implementation("com.airbnb.android:epoxy-paging:$epoxyVersion")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    // Network debugging
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    debugImplementation("com.github.chuckerteam.chucker:library:$chuckerVersion")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:$chuckerVersion")

    // Jetpack Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}