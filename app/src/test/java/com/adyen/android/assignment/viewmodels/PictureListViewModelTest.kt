package com.adyen.android.assignment.viewmodels

import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.repositories.PlanetaryResult
import com.adyen.android.assignment.ui.pictureList.PictureListIntent
import com.adyen.android.assignment.ui.pictureList.PictureListUiState
import com.adyen.android.assignment.usecases.GetPicturesUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.time.LocalDate


/**
 * Test class for [PictureListViewModel].
 *
 * This class uses MockK for mocking dependencies and coroutines test features for testing suspending functions.
 */
@ExperimentalCoroutinesApi
class PictureListViewModelTest {

    private val getPicturesUseCase: GetPicturesUseCase = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: PictureListViewModel

    /**
     * Sets up the test environment before each test.
     *
     * Initializes the ViewModel and mocks the shared flow for SortBy events.
     */
    @Before
    fun setup() {
        viewModel = PictureListViewModel(
            getPicturesUseCase,
            testDispatcher,
        )
    }

    /**
     * Tests that `getPictures` updates `uiState` to [PictureListUiState.Success] when the use case returns [PlanetaryResult.Success].
     */
    @Test
    fun `getPictures updates uiState to Success when use case returns Success`(): Unit = runTest {
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
        coEvery { getPicturesUseCase(any()) } returns PlanetaryResult.Success(pictures)

        viewModel.processIntent(PictureListIntent.GetPictures)

        viewModel.uiState.value shouldBe PictureListUiState.Success(pictures)
    }

    /**
     * Tests that `getPictures` updates `uiState` to [PictureListUiState.Error] when the use case returns [PlanetaryResult.ErrorCode].
     */
    @Test
    fun `getPictures updates uiState to Error when use case returns ErrorCode`() = runTest {
        coEvery { getPicturesUseCase(any()) } returns PlanetaryResult.ErrorCode(400)

        viewModel.processIntent(PictureListIntent.GetPictures)

        viewModel.uiState.value shouldBe PictureListUiState.Error("400", false)
    }

    /**
     * Tests that `getPictures` updates `uiState` to [PictureListUiState.Error] when the use case returns [PlanetaryResult.ErrorIOException].
     */
    @Test
    fun `getPictures updates uiState to Error when use case returns ErrorIOException`() = runTest {
        val message = "io exception"
        coEvery { getPicturesUseCase(any()) } returns PlanetaryResult.ErrorIOException(
            IOException(
                message
            )
        )

        viewModel.processIntent(PictureListIntent.GetPictures)

        viewModel.uiState.value shouldBe PictureListUiState.Error(message, true)
    }

    /**
     * Tests that `getPictures` updates `uiState` to [PictureListUiState.Error] when the use case returns [PlanetaryResult.ErrorException].
     */
    @Test
    fun `getPictures updates uiState to Error when use case returns ErrorException`() = runTest {
        coEvery { getPicturesUseCase(any()) } returns PlanetaryResult.ErrorException(Exception("Test exception"))

        viewModel.processIntent(PictureListIntent.GetPictures)


        viewModel.uiState.value shouldBe PictureListUiState.Error("Test exception", false)
    }

    /**
     * Tests that `getPictures` updates `uiState` to [PictureListUiState.Error] when an exception occurs during the process.
     */
    @Test
    fun `getPictures updates uiState to Error when an exception occurs`() = runTest {
        coEvery { getPicturesUseCase(any()) } throws RuntimeException("Test exception")

        viewModel.processIntent(PictureListIntent.GetPictures)

        viewModel.uiState.value shouldBe PictureListUiState.Error("Test exception")
    }
}