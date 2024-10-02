package com.adyen.android.assignment.usecases

import com.adyen.android.assignment.repositories.PlanetaryRepository
import com.adyen.android.assignment.repositories.PlanetaryResult
import javax.inject.Inject

/**
 * Use case for retrieving a list of astronomy pictures.
 *
 * This use case interacts with the PlanetaryRepository to fetch the pictures.
 *
 * @param planetaryRepository The repository responsible for accessing planetary data.
 */
class GetPicturesUseCase @Inject constructor(private val planetaryRepository: PlanetaryRepository) {

    /**
     * Executes the use case and returns a PlanetaryResult.
     *
     * @return A PlanetaryResult indicating success or failure.
     */
    suspend operator fun invoke(acceptCached: Boolean = false): PlanetaryResult = planetaryRepository.getPictures(acceptCached)
}