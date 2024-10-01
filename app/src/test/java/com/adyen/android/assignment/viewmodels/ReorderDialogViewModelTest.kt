package com.adyen.android.assignment.viewmodels

import com.adyen.android.assignment.ui.redorderDialog.ReorderDialogIntent
import com.adyen.android.assignment.ui.redorderDialog.ReorderDialogUiState
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
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

    private val sharedSortByViewModel: SharedSortByViewModel = mockk(relaxed = true)
    private lateinit var viewModel: ReorderDialogViewModel

    @Before
    fun setup() {
        viewModel = ReorderDialogViewModel(sharedSortByViewModel)
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
     * Tests that `processIntent` with [ReorderDialogIntent.Apply] after [ReorderDialogIntent.SortByDate] calls `emitSortEvent` with [SortBy.DATE].
     */
    @Test
    fun `processIntent Apply with SortByDate calls emitSortEvent with DATE`() = runTest {
        viewModel.processIntent(ReorderDialogIntent.SortByDate)
        every { sharedSortByViewModel.emitSortEvent(any()) } just runs

        viewModel.processIntent(ReorderDialogIntent.Apply)

        verify { sharedSortByViewModel.emitSortEvent(SortBy.DATE) }
    }

    /**
     * Tests that `processIntent` with [ReorderDialogIntent.Apply] after [ReorderDialogIntent.SortByTitle] calls `emitSortEvent` with [SortBy.TITLE].
     */
    @Test
    fun `processIntent Apply with SortByTitle calls emitSortEvent with TITLE`() = runTest {
        viewModel.processIntent(ReorderDialogIntent.SortByTitle)
        every { sharedSortByViewModel.emitSortEvent(any()) } just runs

        viewModel.processIntent(ReorderDialogIntent.Apply)

        verify { sharedSortByViewModel.emitSortEvent(SortBy.TITLE) }
    }

    /**
     * Tests that `processIntent` with [ReorderDialogIntent.Apply] without a prior sort selection does not call `emitSortEvent`.
     */
    @Test
    fun `processIntent Apply with None does not call emitSortEvent`() = runTest {
        every { sharedSortByViewModel.emitSortEvent(any()) } just runs

        viewModel.processIntent(ReorderDialogIntent.Apply)

        verify(inverse = true) { sharedSortByViewModel.emitSortEvent(any()) }
    }
}