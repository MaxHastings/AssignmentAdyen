package com.adyen.android.assignment.usecases

import com.adyen.android.assignment.api.model.AstronomyPicture
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.LocalDate

class SortPicturesByDateUseCaseTest {

    private val useCase = SortPicturesByDateUseCase()

    @Test
    fun `invoke() sorts pictures by date in ascending order`() = runTest {
        val pictures = listOf(
            AstronomyPicture("1", "title1", date = LocalDate.of(2023, 12, 10), mediaType = "", url = "", hdUrl = "", explanation = ""),
            AstronomyPicture("2", "title2", date =LocalDate.of(2023, 12, 1), mediaType = "", url = "", hdUrl = "", explanation = ""),
            AstronomyPicture("3", "title3", date = LocalDate.of(2023, 12, 5), mediaType = "", url = "", hdUrl = "", explanation = "")
        )

        val sortedPictures = useCase(pictures, true)

        assertThat(sortedPictures).isEqualTo(
            listOf(
                AstronomyPicture("2", "title2", date = LocalDate.of(2023, 12, 1), mediaType = "", url = "", hdUrl = "", explanation = ""),
                AstronomyPicture("3", "title3", date = LocalDate.of(2023, 12, 5), mediaType = "", url = "", hdUrl = "", explanation = ""),
                AstronomyPicture("1", "title1", date = LocalDate.of(2023, 12, 10), mediaType = "", url = "", hdUrl = "", explanation = "")
            )
        )
    }

    @Test
    fun `invoke() sorts pictures by date in descending order`() = runTest {
        val pictures = listOf(
            AstronomyPicture("1", "title1", date = LocalDate.of(2023, 12, 10), mediaType = "", url = "", hdUrl = "", explanation = ""),
            AstronomyPicture("2", "title2", date =LocalDate.of(2023, 12, 1), mediaType = "", url = "", hdUrl = "", explanation = ""),
            AstronomyPicture("3", "title3", date = LocalDate.of(2023, 12, 5), mediaType = "", url = "", hdUrl = "", explanation = "")
        )

        val sortedPictures = useCase(pictures, false)

        assertThat(sortedPictures).isEqualTo(
            listOf(
                AstronomyPicture("1", "title1", date = LocalDate.of(2023, 12, 10), mediaType = "", url = "", hdUrl = "", explanation = ""),
                AstronomyPicture("3", "title3", date = LocalDate.of(2023, 12, 5), mediaType = "", url = "", hdUrl = "", explanation = ""),
                AstronomyPicture("2", "title2", date = LocalDate.of(2023, 12, 1), mediaType = "", url = "", hdUrl = "", explanation = "")
            )
        )
    }
}