plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {

    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
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
        //AndroidX
        implementation(coreKtx)
        implementation(appCompat)
        testImplementation(jUnit)
        androidTestImplementation(jUnitTest)
        androidTestImplementation(espressoCore)

        // Retrofit
        implementation(retrofit)
        implementation(okhttp)
        implementation(logginInterceptor)
        implementation(gson)

        //Hilt Dependency injection
        implementation(hilt)
        implementation(hiltCompiler)
        implementation(legacySupport)
        implementation(testCoreKtx)
        kapt(kaptHiltCompiler)
        implementation(hiltLifeCycleViewModel)
        kapt(kaptHiltCompiler)
    }
}