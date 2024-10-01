package com.adyen.android.assignment.repositories

import com.adyen.android.assignment.api.PlanetaryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

/**
 * Repository class for accessing planetary data.
 *
 * This class is responsible for fetching astronomy pictures from the PlanetaryService
 * and handling potential errors.
 *
 * @param planetaryService Service for accessing the planetary API.
 */
class PlanetaryRepository @Inject constructor(
    private val planetaryService: PlanetaryService
) {

    /**
     * Fetches a list of astronomy pictures.
     *
     * @return A PlanetaryResult indicating success or failure.
     *         - Success: Contains the list of pictures.
     *         - ErrorCode: Contains the HTTP error code.
     *         - ErrorIOException: Contains the IOException that occurred.
     *         - ErrorException: Contains the Exception that occurred.
     */
    suspend fun getPictures(): PlanetaryResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = planetaryService.getPictures()
                if (response.isSuccessful) {
                    PlanetaryResult.Success(response.body() ?: emptyList())
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