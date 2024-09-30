package com.adyen.android.assignment.usecases

import com.adyen.android.assignment.api.model.AstronomyPicture

class SortPicturesByDateUseCase {

    operator fun invoke(pictures: List<AstronomyPicture>, ascending: Boolean): List<AstronomyPicture> {
        return if (ascending) {
            pictures.sortedBy { it.date }
        } else {
            pictures.sortedByDescending { it.date }
        }
    }
}