plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {

    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "com.moises.rickymorty"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = 1
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    testOptions.unitTests.isIncludeAndroidResources = true
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        kotlin {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
            }
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
        resources.excludes.add("META-INF/gradle/incremental.annotation.processors")

    }
}

dependencies {
    ProjectDependencies.apply {
        //Modules
        implementation(projects.feature.characters)
        implementation(projects.feature.episodes)


        //AndroidX
        implementation("androidx.core:core-ktx:1.7.0")
        implementation("androidx.appcompat:appcompat:1.4.1")
        implementation("com.google.android.material:material:1.6.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.3")
        implementation("androidx.activity:activity-ktx:1.4.0")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")

        //Navigation Fragments
        implementation(navigationFragmentKtx)
        implementation(navigationUiKtx)

//        // Feature module Support
//        implementation("androidx.navigation:navigation-dynamic-features-fragment:2.4.2")

        //Hilt Dependencies injection
        implementation("com.google.dagger:hilt-android:2.40.5")
        implementation("androidx.legacy:legacy-support-v4:1.0.0")
        implementation("androidx.test:core-ktx:1.4.0")
        kapt("com.google.dagger:hilt-android-compiler:2.40.5")
        implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
        kapt("androidx.hilt:hilt-compiler:1.0.0")
        implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")

        // Robolectric environment
        testImplementation("org.robolectric:robolectric:4.4")

        //Canary Memory Leak
        debugImplementation(canaryLeak)
    }

}