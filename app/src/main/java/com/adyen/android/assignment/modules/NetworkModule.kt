package com.adyen.android.assignment.modules

import com.adyen.android.assignment.BuildConfig
import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.DayAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Hilt module for providing network-related dependencies.
 *
 * This module provides instances of Moshi, Retrofit, and PlanetaryService.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a Moshi instance configured with a custom DayAdapter and KotlinJsonAdapterFactory.
     *
     * @return A Moshi instance.
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(DayAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    /**
     * Provides a Retrofit instance configured with the NASA base URL and MoshiConverterFactory.
     *
     * @param moshi The Moshi instance to use for serialization and deserialization.
     * @return A Retrofit instance.
     */
    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NASA_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /**
     * Provides a PlanetaryService instance using the provided Retrofit instance.
     *
     * @param retrofit The Retrofit instance to use for creating the service.
     * @return A PlanetaryService instance.
     */
    @Provides
    @Singleton
    fun providePlanetaryService(retrofit: Retrofit): PlanetaryService {
        return retrofit.create(PlanetaryService::class.java)
    }
}