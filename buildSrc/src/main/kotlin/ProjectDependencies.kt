object ProjectDependencies {
    //AndroidX
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    val appCompat = "androidx.appcompat:appcompat:1.4.1"
    val materialDesign = "com.google.android.material:material:1.6.0"
    val contraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
    val activityKtx = "androidx.activity:activity-ktx:1.4.0"
    val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1"
    val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
    val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    val pagingRuntime = "androidx.paging:paging-runtime:3.1.1"
    val fragmentKtx = "androidx.fragment:fragment-ktx:1.4.1"

    //Navigation Fragments
    val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}"
    val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}"

    // Feature module Support
    val navigationDynamicFeaturesFragment = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navVersion}"

    //Hilt Dependencies injection
    val hilt = "com.google.dagger:hilt-android:2.40.5"
    val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:2.40.5"
    val hiltLifeCycleViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
    val kaptHiltCompiler = "androidx.hilt:hilt-compiler:1.0.0"
    val hiltNavigationFragment = "androidx.hilt:hilt-navigation-fragment:1.0.0"

    //Glide image loader
    val glide = "com.github.bumptech.glide:glide:4.12.0"

    // DB Room
    val roomRuntime = "androidx.room:room-runtime:2.4.2"
    val roomPaging = "androidx.room:room-paging:2.4.2"
    val kaptRoomCompiler = "androidx.room:room-compiler:2.4.2"

    // Kotlin Extensions and Coroutines support for Room
    val roomKtx = "androidx.room:room-ktx:2.4.2"

    //Test
    val jUnit = "junit:junit:4.13.2"
    val jUnitTest = "androidx.test.ext:junit:1.1.3"
    val testCoreKtx = "androidx.test:core-ktx:1.4.0"
    val jUnitKtx = "androidx.test.ext:junit-ktx:1.1.3"
    val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
    val mockk = "io.mockk:mockk:1.12.3"

    // Robolectric environment
    val roboeletric = "org.robolectric:robolectric:4.4"

    //Canary Memory Leak
    val canaryLeak = "com.squareup.leakcanary:leakcanary-android:2.8.1"
    
    //Retrofit
    val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    val okhttp =  "com.squareup.okhttp3:okhttp:5.0.0-alpha.3"
    val logginInterceptor =  "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3"
    val gson = "com.squareup.retrofit2:converter-gson:2.9.0"
}