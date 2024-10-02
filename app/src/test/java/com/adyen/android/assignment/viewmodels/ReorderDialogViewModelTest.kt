package com.adyen.android.assignment.viewmodels

import com.adyen.android.assignment.ui.redorderDialog.ReorderDialogIntent
import com.adyen.android.assignment.ui.redorderDialog.ReorderDialogUiState
import com.adyen.android.assignment.usecases.SortPicturesUseCase
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Test class for [ReorderDialogViewModel].
 *
 * This class uses MockK for mocking dependencies and coroutines test features for testing suspending functions.
 */
@ExperimentalCoroutinesApi
class ReorderDialogViewModelTest {

    private val sortPicturesUseCase: SortPicturesUseCase = mockk(relaxed = true)
    private lateinit var viewModel: ReorderDialogViewModel

    @Before
    fun setup() {
        viewModel = ReorderDialogViewModel(sortPicturesUseCase)
    }

    /**
     * Tests that `processIntent` with [ReorderDialogIntent.SortByDate] updates `uiState` to [ReorderDialogUiState.SortByDate].
     */
    @Test
    fun `processIntent SortByDate updates uiState to SortByDate`() = runTest {
        viewModel.processIntent(ReorderDialogIntent.SortByDate)

        viewModel.uiState.value shouldBe ReorderDialogUiState.SortByDate
    }

    /**
     * Tests that `processIntent` with [ReorderDialogIntent.SortByTitle] updates `uiState` to [ReorderDialogUiState.SortByTitle].
     */
    @Test
    fun `processIntent SortByTitle updates uiState to SortByTitle`() = runTest {
        viewModel.processIntent(ReorderDialogIntent.SortByTitle)

        viewModel.uiState.value shouldBe ReorderDialogUiState.SortByTitle
    }

    /**
     * Tests that `processIntent` with [ReorderDialogIntent.Apply] after [ReorderDialogIntent.SortByDate] calls [SortPicturesUseCase].
     */
    @Test
    fun `processIntent Apply with SortByDate calls emitSortEvent with DATE`() = runTest {
        viewModel.processIntent(ReorderDialogIntent.SortByDate)

        viewModel.processIntent(ReorderDialogIntent.Apply)

        verify { sortPicturesUseCase(any(), true) }
    }

    /**
     * Tests that `processIntent` with [ReorderDialogIntent.Apply] after [ReorderDialogIntent.SortByTitle] calls [SortPicturesUseCase].
     */
    @Test
    fun `processIntent Apply with SortByTitle calls emitSortEvent with TITLE`() = runTest {
        viewModel.processIntent(ReorderDialogIntent.SortByTitle)

        viewModel.processIntent(ReorderDialogIntent.Apply)

        verify { sortPicturesUseCase(any(), true) }
    }

    /**
     * Tests that `processIntent` with [ReorderDialogIntent.Apply] without a prior sort selection does not call [SortPicturesUseCase].
     */
    @Test
    fun `processIntent Apply with None does not call emitSortEvent`() = runTest {

        viewModel.processIntent(ReorderDialogIntent.Apply)

        verify(inverse = true) { sortPicturesUseCase.invoke(any(), any()) }

    }
}