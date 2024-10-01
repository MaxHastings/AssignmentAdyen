package com.adyen.android.assignment.repositories

import com.adyen.android.assignment.api.model.AstronomyPicture
import java.io.IOException

/**
 * Sealed class representing the result of a planetary data request.
 */
sealed class PlanetaryResult {
    /**
     * Represents a successful result with a list of astronomy pictures.
     *
     * @param pictures The list of astronomy pictures retrieved.
     */
    data class Success(val pictures: List<AstronomyPicture>) : PlanetaryResult()

    /**
     * Represents a result with an HTTP error code.
     *
     * @param code The HTTP error code.
     */
    data class ErrorCode(val code: Int) : PlanetaryResult()

    /**
     * Represents a result with an IOException.
     *
     * @param e The IOException that occurred.
     */
    data class ErrorIOException(val e: IOException) : PlanetaryResult()

    /**
     * Represents a failure result due to a general Exception.
     *
     * @param e The Exception that occurred.
     */
    data class ErrorException(val e: Exception) : PlanetaryResult()
}