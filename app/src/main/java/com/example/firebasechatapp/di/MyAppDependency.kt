package com.example.firebasechatapp.di

import com.example.firebasechatapp.repositories.RealtimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyAppDependency {

    @Provides
    @Singleton
    fun getRealtimeRepository(): RealtimeRepository {
        return RealtimeRepository()
    }
}