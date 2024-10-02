package com.adyen.android.assignment.usecases

import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.repositories.PlanetaryRepository
import javax.inject.Inject

/**
 * Use case for sorting a list of astronomy pictures by a specified field.
 *
 * This use case takes a list of pictures, a selector function to define the sorting key,
 * and a boolean indicating whether to sort in ascending or descending order.
 */
class SortPicturesUseCase @Inject constructor(
    private val planetaryRepository: PlanetaryRepository
) {

    /**
     * Executes the use case and returns a sorted list of pictures.
     *
     * @param pictures The list of pictures to sort.
     * @param selector A function that selects the field to sort by from an AstronomyPicture.
     * @param ascending True to sort in ascending order, false to sort in descending order.
     * @return A sorted list of pictures.
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