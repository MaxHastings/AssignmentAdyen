package com.adyen.android.assignment.usecases

import com.adyen.android.assignment.api.model.AstronomyPicture
import javax.inject.Inject

/**
 * Use case for sorting a list of astronomy pictures by date.
 *
 * This use case takes a list of pictures and a boolean indicating whether to sort in ascending
 * or descending order.
 */
class SortPicturesByDateUseCase @Inject constructor() {

    /**
     * Executes the use case and returns a sorted list of pictures.
     *
     * @param pictures The list of pictures to sort.
     * @param ascending True to sort in ascending order, false to sort in descending order.
     * @return A sorted list of pictures.
     */
    operator fun invoke(
        pictures: List<AstronomyPicture>,
        ascending: Boolean
    ): List<AstronomyPicture> {
        return if (ascending) {
            pictures.sortedBy { it.date }
        } else {
            pictures.sortedByDescending { it.date }
        }
    }
}