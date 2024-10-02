package com.adyen.android.assignment.usecases

import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.repositories.PlanetaryRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import java.time.LocalDate

/**
 * Test class for [SortPicturesUseCase].
 *
 * This class tests the functionality of the `invoke()` method for sorting pictures by date.
 */
class SortPicturesUseCaseTest {

    private val planetaryRepository: PlanetaryRepository = mockk()
    private val useCase = SortPicturesUseCase(planetaryRepository)

    /**
     * Tests that `invoke()` sorts pictures by title in ascending order when `ascending` is true.
     */
    @Test
    fun `invoke() sorts pictures by title in ascending order`() {
        val pictures = listOf(
            AstronomyPicture("1", "title1", date = LocalDate.of(2023, 12, 10), mediaType = "", url = "", hdUrl = "", explanation = ""),
            AstronomyPicture("2", "title2", date = LocalDate.of(2023, 12, 1), mediaType = "", url = "", hdUrl = "", explanation = ""),
            AstronomyPicture("3", "title3", date = LocalDate.of(2023, 12, 5), mediaType = "", url = "", hdUrl = "", explanation = "")
        )
        every { planetaryRepository.getCachedPictures() } returns pictures
        every { planetaryRepository.setCachedPictures(any()) } just Runs

        useCase({ it.title }, true)

        verify {
            planetaryRepository.setCachedPictures(
                listOf(
                    AstronomyPicture("1", "title1", date = LocalDate.of(2023, 12, 10), mediaType = "", url = "", hdUrl = "", explanation = ""),
                    AstronomyPicture("2", "title2", date = LocalDate.of(2023, 12, 1), mediaType = "", url = "", hdUrl = "", explanation = ""),
                    AstronomyPicture("3", "title3", date = LocalDate.of(2023, 12, 5), mediaType = "", url = "", hdUrl = "", explanation = "")
                )
            )
        }
    }

    /**
     * Tests that `invoke()` sorts pictures by title in descending order when `ascending` is false.
     */
    @Test
    fun `invoke() sorts pictures by title in descending order`() {
        val pictures = listOf(
            AstronomyPicture("1", "title1", date = LocalDate.of(2023, 12, 10), mediaType = "", url = "", hdUrl = "", explanation = ""),
            AstronomyPicture("2", "title2", date = LocalDate.of(2023, 12, 1), mediaType = "", url = "", hdUrl = "", explanation = ""),
            AstronomyPicture("3", "title3", date = LocalDate.of(2023, 12, 5), mediaType = "", url = "", hdUrl = "", explanation = "")
        )
        every { planetaryRepository.getCachedPictures() } returns pictures
        every { planetaryRepository.setCachedPictures(any()) } just Runs

        useCase({ it.title }, false)

        verify {
            planetaryRepository.setCachedPictures(
                listOf(
                    AstronomyPicture("3", "title3", date = LocalDate.of(2023, 12, 5), mediaType = "", url = "", hdUrl = "", explanation = ""),
                    AstronomyPicture("2", "title2", date = LocalDate.of(2023, 12, 1), mediaType = "", url = "", hdUrl = "", explanation = ""),
                    AstronomyPicture("1", "title1", date = LocalDate.of(2023, 12, 10), mediaType = "", url = "", hdUrl = "", explanation = "")
                )
            )
        }
    }

    /**
     * Tests that `invoke()` sorts pictures by date in ascending order when `ascending` is true.
     */
    @Test
    fun `invoke() sorts pictures by date in ascending order`() {
        val pictures = listOf(
            AstronomyPicture("1", "title1", date = LocalDate.of(2023, 12, 10), mediaType = "", url = "", hdUrl = "", explanation = ""),
            AstronomyPicture("2", "title2", date = LocalDate.of(2023, 12, 1), mediaType = "", url = "", hdUrl = "", explanation = ""),
            AstronomyPicture("3", "title3", date = LocalDate.of(2023, 12, 5), mediaType = "", url = "", hdUrl = "", explanation = "")
        )
        every { planetaryRepository.getCachedPictures() } returns pictures
        every { planetaryRepository.setCachedPictures(any()) } just Runs

        useCase({ it.date }, true)

        verify {
            planetaryRepository.setCachedPictures(
                listOf(
                    AstronomyPicture("2", "title2", date = LocalDate.of(2023, 12, 1), mediaType = "", url = "", hdUrl = "", explanation = ""),
                    AstronomyPicture("3", "title3", date = LocalDate.of(2023, 12, 5), mediaType = "", url = "", hdUrl = "", explanation = ""),
                    AstronomyPicture("1", "title1", date = LocalDate.of(2023, 12, 10), mediaType = "", url = "", hdUrl = "", explanation = "")
                )
            )
        }
    }

    /**
     * Tests that `invoke()` sorts pictures by date in descending order when `ascending` is false.
     */
    @Test
    fun `invoke() sorts pictures by date in descending order`() {
        val pictures = listOf(
            AstronomyPicture("1", "title1", date = LocalDate.of(2023, 12, 10), mediaType = "", url = "", hdUrl = "", explanation = ""),
            AstronomyPicture("2", "title2", date = LocalDate.of(2023, 12, 1), mediaType = "", url = "", hdUrl = "", explanation = ""),
            AstronomyPicture("3", "title3", date = LocalDate.of(2023, 12, 5), mediaType = "", url = "", hdUrl = "", explanation = "")
        )
        every { planetaryRepository.getCachedPictures() } returns pictures
        every { planetaryRepository.setCachedPictures(any()) } just Runs

        useCase({ it.date }, false)

        verify {
            planetaryRepository.setCachedPictures(
                listOf(
                    AstronomyPicture("1", "title1", date = LocalDate.of(2023, 12, 10), mediaType = "", url = "", hdUrl = "", explanation = ""),
                    AstronomyPicture("3", "title3", date = LocalDate.of(2023, 12, 5), mediaType = "", url = "", hdUrl = "", explanation = ""),
                    AstronomyPicture("2", "title2", date = LocalDate.of(2023, 12, 1), mediaType = "", url = "", hdUrl = "", explanation = "")
                )
            )
        }
    }
}