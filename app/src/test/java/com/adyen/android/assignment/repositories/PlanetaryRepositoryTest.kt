package com.adyen.android.assignment.repositories

import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.time.LocalDate

/**
 * Test class for [PlanetaryRepository].
 *
 * This class uses MockK for mocking dependencies and coroutines test features for testing suspending functions.
 */
@ExperimentalCoroutinesApi
class PlanetaryRepositoryTest {

    private val planetaryService: PlanetaryService = mockk()
    private lateinit var repository: PlanetaryRepository

    @Before
    fun setup() {
        repository = PlanetaryRepository(planetaryService)
    }

    /**
     * Tests that [PlanetaryRepository.getPictures] returns [PlanetaryResult.Success] when the response is successful.
     */
    @Test
    fun `getPictures returns Success when response is successful`() = runTest {
        val pictures = listOf(
            AstronomyPicture(
                serviceVersion = "v1",
                title = "Test Picture",
                explanation = "Test explanation",
                date = LocalDate.now(),
                mediaType = "image",
                hdUrl = "hdurl",
                url = "url"
            )
        )
        val response: Response<List<AstronomyPicture>> = Response.success(pictures)
        coEvery { planetaryService.getPictures() } returns response

        val result = repository.getPictures()

        coVerify { planetaryService.getPictures() }
        assertEquals(PlanetaryResult.Success(pictures), result)
    }

    /**
     * Tests that [PlanetaryRepository.getPictures] returns [PlanetaryResult.ErrorCode] when the response is not successful.
     */
    @Test
    fun `getPictures returns ErrorCode when response is not successful`() = runTest {
        val response: Response<List<AstronomyPicture>> = Response.error(
            400,
            "error".toResponseBody("text/plain".toMediaTypeOrNull())
        )
        coEvery { planetaryService.getPictures() } returns response

        val result = repository.getPictures()

        coVerify { planetaryService.getPictures() }
        assertEquals(PlanetaryResult.ErrorCode(400), result)
    }

    /**
     * Tests that [PlanetaryRepository.getPictures] returns [PlanetaryResult.ErrorIOException] when an [IOException] occurs.
     */
    @Test
    fun `getPictures returns ErrorIOException when IOException occurs`() = runTest {
        coEvery { planetaryService.getPictures() } throws IOException()

        val result = repository.getPictures()

        coVerify { planetaryService.getPictures() }
        assertEquals(true, result is PlanetaryResult.ErrorIOException)
    }

    /**
     * Tests that [PlanetaryRepository.getPictures] returns [PlanetaryResult.ErrorException] when an [Exception] occurs.
     */
    @Test
    fun `getPictures returns ErrorException when Exception occurs`() = runTest {
        coEvery { planetaryService.getPictures() } throws Exception()

        val result = repository.getPictures()

        coVerify { planetaryService.getPictures() }
        assertEquals(true, result is PlanetaryResult.ErrorException)
    }
}