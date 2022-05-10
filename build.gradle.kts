buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navVersion}")
    }
    repositories {
        google()
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application").version(Versions.gradle).apply(false)
    id("com.android.library").version(Versions.gradle).apply(false)
    id("org.jetbrains.kotlin.android").version("1.6.21").apply(false)
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}
