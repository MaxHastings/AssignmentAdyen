package com.adyen.android.assignment.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Hilt module for providing a CoroutineDispatcher.
 *
 * This module provides an instance of CoroutineDispatcher, specifically Dispatchers.IO,
 * which is suitable for I/O operations like network requests or database interactions.
 */
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    /**
     * Provides a CoroutineDispatcher instance.
     *
     * @return A CoroutineDispatcher instance (Dispatchers.IO).
     */
    @Provides
    fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}