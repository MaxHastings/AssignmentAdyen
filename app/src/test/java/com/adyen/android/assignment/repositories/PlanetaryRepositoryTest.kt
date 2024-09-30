package com.adyen.android.assignment.repositories

import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PlanetaryRepositoryTest {

    private lateinit var planetaryService: PlanetaryService

    private lateinit var repository: PlanetaryRepository

    @Before
    fun setup() {
        planetaryService = mockk()
        repository = PlanetaryRepository(planetaryService)
    }

    @Test
    fun `getPictures returns success result`() = runTest {
        val response = Response.success(emptyList<AstronomyPicture>())
        coEvery { planetaryService.getPictures() } returns response

        val result = repository.getPictures()

        Assert.assertTrue(result is PlanetaryResult.Success)
    }

}