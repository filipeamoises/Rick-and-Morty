package com.moises.rickymorty.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


// This app has only one activity because it and to make it simpler all dependencies configuration will be attached to the application.
// If had more activities I could create a module for a specific activity
@Module
@InstallIn(SingletonComponent::class)
object AppModule