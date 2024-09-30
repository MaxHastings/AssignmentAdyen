package com.adyen.android.assignment.usecases

import com.adyen.android.assignment.repositories.PlanetaryRepository
import com.adyen.android.assignment.repositories.PlanetaryResult
import javax.inject.Inject

class GetPicturesUseCase @Inject constructor(private val planetaryRepository: PlanetaryRepository) {
    suspend operator fun invoke(): PlanetaryResult = planetaryRepository.getPictures()
}