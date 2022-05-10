object ProjectDependencies {
    //AndroidX
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidXLifecycle}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidXLifecycle}"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.pagingRuntime}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.appCompat}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.appCompat}"

    //Navigation Fragments
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}"

    //Hilt Dependencies injection
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupport}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltLifeCycleViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltViewModel}"
    const val kaptHiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"
    const val hiltNavigationFragment = "androidx.hilt:hilt-navigation-fragment:${Versions.hiltCompiler}"

    //Glide image loader
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"

    // DB Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomPaging = "androidx.room:room-paging:${Versions.room}"
    const val kaptRoomCompiler = "androidx.room:room-compiler:${Versions.room}"

    // Kotlin Extensions and Coroutines support for Room
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    //Test
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val jUnitTest = "androidx.test.ext:junit:${Versions.jUnitTest}"
    const val testCoreKtx = "androidx.test:core-ktx:${Versions.testCoreKtx}"
    const val jUnitKtx = "androidx.test.ext:junit-ktx:${Versions.jUnitTest}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val androidSupportTestRunner = "com.android.support.test:runner:${Versions.testRunner}"
    const val androidSupportTestRules = "com.android.support.test:rules:${Versions.testRunner}"
    const val androidxTestRunner = "androidx.test:runner:${Versions.androidXTestRunner}"

    // Robolectric environment
    const val roboeletric = "org.robolectric:robolectric:${Versions.robolectric}"

    //Canary Memory Leak
    const val canaryLeak = "com.squareup.leakcanary:leakcanary-android:${Versions.canaryLeak}"

    //Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val logginInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
}