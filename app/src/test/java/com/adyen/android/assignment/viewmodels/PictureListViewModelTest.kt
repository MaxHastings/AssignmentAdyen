package com.adyen.android.assignment.viewmodels

import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.repositories.PlanetaryResult
import com.adyen.android.assignment.ui.pictureList.PictureListIntent
import com.adyen.android.assignment.ui.pictureList.PictureListUiState
import com.adyen.android.assignment.usecases.GetPicturesUseCase
import com.adyen.android.assignment.usecases.SortPicturesByDateUseCase
import com.adyen.android.assignment.usecases.SortPicturesByTitleUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
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
    private val sortPicturesByTitleUseCase: SortPicturesByTitleUseCase = mockk()
    private val sortPicturesByDateUseCase: SortPicturesByDateUseCase = mockk()
    private val sharedSortByViewModel: SharedSortByViewModel = mockk(relaxed = true)
    private val mockSharedFlow = MutableSharedFlow<SortBy>()
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: PictureListViewModel

    /**
     * Sets up the test environment before each test.
     *
     * Initializes the ViewModel and mocks the shared flow for SortBy events.
     */
    @Before
    fun setup() {
        every { sharedSortByViewModel.sortEvents } returns mockSharedFlow
        viewModel = PictureListViewModel(
            getPicturesUseCase,
            sortPicturesByTitleUseCase,
            sortPicturesByDateUseCase,
            sharedSortByViewModel,
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
        coEvery { getPicturesUseCase() } returns PlanetaryResult.Success(pictures)

        viewModel.getPictures()

        viewModel.uiState.value shouldBe PictureListUiState.Success(pictures)
    }

    /**
     * Tests that `getPictures` updates `uiState` to [PictureListUiState.Error] when the use case returns [PlanetaryResult.ErrorCode].
     */
    @Test
    fun `getPictures updates uiState to Error when use case returns ErrorCode`() = runTest {
        coEvery { getPicturesUseCase() } returns PlanetaryResult.ErrorCode(400)

        viewModel.processIntent(PictureListIntent.GetPictures)

        viewModel.uiState.value shouldBe PictureListUiState.Error("400", false)
    }

    /**
     * Tests that `getPictures` updates `uiState` to [PictureListUiState.Error] when the use case returns [PlanetaryResult.ErrorIOException].
     */
    @Test
    fun `getPictures updates uiState to Error when use case returns ErrorIOException`() = runTest {
        val message = "io exception"
        coEvery { getPicturesUseCase() } returns PlanetaryResult.ErrorIOException(IOException(message))

        viewModel.processIntent(PictureListIntent.GetPictures)

        viewModel.uiState.value shouldBe PictureListUiState.Error(message, true)
    }

    /**
     * Tests that `getPictures` updates `uiState` to [PictureListUiState.Error] when the use case returns [PlanetaryResult.ErrorException].
     */
    @Test
    fun `getPictures updates uiState to Error when use case returns ErrorException`() = runTest {
        coEvery { getPicturesUseCase() } returns PlanetaryResult.ErrorException(Exception("Test exception"))

        viewModel.processIntent(PictureListIntent.GetPictures)


        viewModel.uiState.value shouldBe PictureListUiState.Error("Test exception", false)
    }

    /**
     * Tests that `getPictures` updates `uiState` to [PictureListUiState.Error] when an exception occurs during the process.
     */
    @Test
    fun `getPictures updates uiState to Error when an exception occurs`() = runTest {
        coEvery { getPicturesUseCase() } throws RuntimeException("Test exception")

        viewModel.processIntent(PictureListIntent.GetPictures)

        viewModel.uiState.value shouldBe PictureListUiState.Error("Test exception")
    }

    /**
     * Tests that `sortPictures` sorts pictures by date when `sortBy` is [SortBy.DATE].
     */
    @Test
    fun `sortPictures sorts pictures by date when sortBy is DATE`() = runTest {
        val pictures = listOf(
            AstronomyPicture(
                serviceVersion = "v1",
                title = "Picture 2",
                explanation = "Test explanation 2",
                date = LocalDate.now().minusDays(1),
                mediaType = "image",
                hdUrl = "hdurl",
                url = "url"
            ),
            AstronomyPicture(
                serviceVersion = "v1",
                title = "Picture 1",
                explanation = "Test explanation 1",
                date = LocalDate.now(),
                mediaType = "image",
                hdUrl = "hdurl",
                url = "url"
            )
        )

        val sortedPictures = listOf(pictures[1], pictures[0])

        coEvery { getPicturesUseCase() } returns PlanetaryResult.Success(pictures)

        viewModel.getPictures()

        coEvery { sortPicturesByDateUseCase.invoke(pictures, true) } returns sortedPictures

        mockSharedFlow.emit(SortBy.DATE)

        viewModel.uiState.value shouldBe PictureListUiState.Success(sortedPictures)
    }

    /**
     * Tests that `sortPictures` sorts pictures by title when `sortBy` is [SortBy.TITLE].
     */
    @Test
    fun `sortPictures sorts pictures by title when sortBy is TITLE`() = runTest {
        val pictures = listOf(
            AstronomyPicture(
                serviceVersion = "v1",
                title = "Picture 2",
                explanation = "Test explanation 2",
                date = LocalDate.now().minusDays(1),
                mediaType = "image",
                hdUrl = "hdurl",
                url = "url"
            ),
            AstronomyPicture(
                serviceVersion = "v1",
                title = "Picture 1",
                explanation = "Test explanation 1",
                date = LocalDate.now(),
                mediaType = "image",
                hdUrl = "hdurl",
                url = "url"
            )
        )

        val sortedPictures = listOf(pictures[1], pictures[0])

        coEvery { getPicturesUseCase() } returns PlanetaryResult.Success(pictures)

        viewModel.getPictures()

        coEvery { sortPicturesByTitleUseCase.invoke(pictures, true) } returns sortedPictures

        mockSharedFlow.emit(SortBy.TITLE)

        viewModel.uiState.value shouldBe PictureListUiState.Success(sortedPictures)
    }
}