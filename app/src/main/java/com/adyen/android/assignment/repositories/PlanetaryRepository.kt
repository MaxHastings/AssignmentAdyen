package com.adyen.android.assignment.repositories

import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class for accessing planetary data.
 *
 * This class is responsible for fetching astronomy pictures from the PlanetaryService
 * and handling potential errors.
 *
 * @param planetaryService Service for accessing the planetary API.
 */
@Singleton
class PlanetaryRepository @Inject constructor(
    private val planetaryService: PlanetaryService
) {
    private var cachedPictures: List<AstronomyPicture> = emptyList()

    /**
     * Fetches a list of astronomy pictures.
     *
     * This function checks if there are cached pictures available and if acceptCached is true.
     * If so, it returns the cached data. Otherwise, it fetches the pictures from the network
     * and caches them.
     *
     * @param acceptCached If true, cached pictures will be returned if available.
     * @return A PlanetaryResult indicating success or failure.
     *         - Success: Contains the list of pictures.
     *         - ErrorCode: Contains the HTTP error code.
     *         - ErrorIOException: Contains the IOException that occurred.
     *         - ErrorException: Contains the Exception that occurred.
     */
    suspend fun getPictures(acceptCached: Boolean = true): PlanetaryResult {
        return if (acceptCached && cachedPictures.isNotEmpty()) {
            PlanetaryResult.Success(cachedPictures)
        } else {
            withContext(Dispatchers.IO) {
                try {
                    val response = planetaryService.getPictures()
                    if (response.isSuccessful) {
                        cachedPictures = response.body() ?: emptyList()
                        PlanetaryResult.Success(cachedPictures)
                    } else {
                        PlanetaryResult.ErrorCode(response.code())
                    }
                } catch (e: IOException) {
                    PlanetaryResult.ErrorIOException(e)
                } catch (e: Exception) {
                    PlanetaryResult.ErrorException(e)
                }
            }
        }
    }

    /**
     * Gets the cached pictures.
     *
     * @return The cached pictures.
     */
    fun getCachedPictures(): List<AstronomyPicture> = cachedPictures

    /**
     * Sets the cached pictures.
     *
     * @param pictures The pictures to cache.
     */
    fun setCachedPictures(pictures: List<AstronomyPicture>) {
        cachedPictures = pictures
    }
}