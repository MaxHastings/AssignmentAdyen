package com.adyen.android.assignment.repositories

import com.adyen.android.assignment.api.model.AstronomyPicture
import java.io.IOException

sealed class PlanetaryResult {
    data class Success(val pictures: List<AstronomyPicture>) : PlanetaryResult()
    data class ErrorCode(val code: Int) : PlanetaryResult()
    data class ErrorIOException(val e: IOException) : PlanetaryResult()
    data class ErrorException(val e: Exception) : PlanetaryResult()
}