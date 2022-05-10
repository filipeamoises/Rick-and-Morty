plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
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
        implementation(coreKtx)
        implementation(appCompat)
        implementation(constraintLayout)
       // implementation(activityKtx)
        implementation(lifecycleRuntimeKtx)

        //Navigation Fragments
        implementation(navigationFragmentKtx)
        implementation(navigationUiKtx)

        //Hilt Dependencies injection
        implementation(hilt)
        implementation(legacySupport)
        implementation(testCoreKtx)
        kapt(hiltCompiler)
        implementation(hiltLifeCycleViewModel)
        kapt(kaptHiltCompiler)
        implementation(hiltNavigationFragment)

        // Robolectric environment
        testImplementation(roboeletric)

        //Canary Memory Leak
        debugImplementation(canaryLeak)

        //Tests
        testImplementation(jUnit)
        testImplementation(jUnitTest)
        testImplementation(testCoreKtx)
        testImplementation(mockk)
        testImplementation(jUnitKtx)
        androidTestImplementation(espressoCore)
        testImplementation(androidSupportTestRunner)
        testImplementation(androidSupportTestRules)
        testImplementation(androidxTestRunner)
    }

}