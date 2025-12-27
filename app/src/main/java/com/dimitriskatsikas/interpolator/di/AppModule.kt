package com.dimitriskatsikas.interpolator.di

import com.dimitriskatsikas.interpolator.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @VersionName
    fun provideVersionName(): String = BuildConfig.VERSION_NAME
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class VersionName
