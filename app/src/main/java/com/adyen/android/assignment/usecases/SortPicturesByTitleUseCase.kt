package com.adyen.android.assignment.usecases

import com.adyen.android.assignment.api.model.AstronomyPicture
import javax.inject.Inject

class SortPicturesByTitleUseCase @Inject constructor() {
    operator fun invoke(pictures: List<AstronomyPicture>, ascending: Boolean): List<AstronomyPicture> {
        return if (ascending) {
            pictures.sortedBy { it.title }
        } else {
            pictures.sortedByDescending { it.title }
        }
    }
}