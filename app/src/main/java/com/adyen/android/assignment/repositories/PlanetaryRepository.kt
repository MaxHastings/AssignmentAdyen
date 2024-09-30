package com.adyen.android.assignment.repositories

import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlanetaryRepository @Inject constructor(
    private val planetaryService: PlanetaryService
) {

    private val _items = MutableStateFlow<List<AstronomyPicture>>(emptyList())
    val items: StateFlow<List<AstronomyPicture>> = _items.asStateFlow()

    suspend fun getPictures(): PlanetaryResult {
        return withContext(Dispatchers.IO) {
            try {
                val response = planetaryService.getPictures()
                if (response.isSuccessful) {
                    _items.value = response.body() ?: emptyList()
                    PlanetaryResult.Success
                } else {
                    PlanetaryResult.ErrorCode(response.code())
                }
            } catch (e: Exception) {
                PlanetaryResult.ErrorException(e)
            }
        }
    }

    fun sortPictures(by: SortBy) {
        val sortedList = when (by) {
            SortBy.DATE -> _items.value.sortedBy { it.date }
            SortBy.TITLE -> _items.value.sortedBy { it.title }
        }
        _items.value = sortedList
    }
}

enum class SortBy {
    DATE, TITLE
}