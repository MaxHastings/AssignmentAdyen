package com.adyen.android.assignment.repositories

import com.adyen.android.assignment.api.PlanetaryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlanetaryRepository @Inject constructor(private val planetaryService: PlanetaryService) {

    suspend fun getPictures(): PlanetaryResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = planetaryService.getPictures()
                if (response.isSuccessful) {
                    PlanetaryResult.Success(response.body() ?: emptyList())
                } else {
                    PlanetaryResult.ErrorCode(response.code())
                }
            } catch (e: Exception) {
                PlanetaryResult.ErrorException(e)
            }
        }
    }
}