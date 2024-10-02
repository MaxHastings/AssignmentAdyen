package com.adyen.android.assignment.usecases

import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.repositories.PlanetaryRepository
import javax.inject.Inject

/**
 * Use case for sorting cached astronomy pictures by a specified field.
 *
 * This use case retrieves cached pictures from the repository, sorts them based on the provided
 * selector function and ascending/descending flag, and updates the cached pictures in the repository.
 */
class SortPicturesUseCase @Inject constructor(
    private val planetaryRepository: PlanetaryRepository
) {

    /**
     * Sorts the cached pictures and updates the repository.
     *
     * @param selector A function that selects the field to sort by for astronomy pictures.
     * @param ascending True to sort in ascending order, false to sort in descending order.
     */
    operator fun invoke(
        selector: (AstronomyPicture) -> Comparable<*>,
        ascending: Boolean
    ) {
        val pictures = planetaryRepository.getCachedPictures()
        val sortedPictures = if (ascending) {
            pictures.sortedWith(compareBy(selector))
        } else {
            pictures.sortedWith(compareByDescending(selector))
        }
        planetaryRepository.setCachedPictures(sortedPictures)
    }
}