package com.adyen.android.assignment.viewmodels

import com.adyen.android.assignment.ui.redorderDialog.ReorderDialogIntent
import com.adyen.android.assignment.ui.redorderDialog.ReorderDialogUiState
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ReorderDialogViewModelTest {

    private val sharedSortByViewModel: SharedSortByViewModel = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ReorderDialogViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ReorderDialogViewModel(sharedSortByViewModel)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `processIntent SortByDate updates uiState to SortByDate`() = runTest {
        viewModel.processIntent(ReorderDialogIntent.SortByDate)

        viewModel.uiState.value shouldBe ReorderDialogUiState.SortByDate
    }

    @Test
    fun `processIntent SortByTitle updates uiState to SortByTitle`() = runTest {
        viewModel.processIntent(ReorderDialogIntent.SortByTitle)

        viewModel.uiState.value shouldBe ReorderDialogUiState.SortByTitle
    }

    @Test
    fun `processIntent Apply with SortByDate calls emitSortEvent with DATE`() = runTest {
        viewModel.processIntent(ReorderDialogIntent.SortByDate)
        every { sharedSortByViewModel.emitSortEvent(any()) } just runs

        viewModel.processIntent(ReorderDialogIntent.Apply)

        verify { sharedSortByViewModel.emitSortEvent(SortBy.DATE) }
    }

    @Test
    fun `processIntent Apply with SortByTitle calls emitSortEvent with TITLE`() = runTest {
        viewModel.processIntent(ReorderDialogIntent.SortByTitle)
        every { sharedSortByViewModel.emitSortEvent(any()) } just runs

        viewModel.processIntent(ReorderDialogIntent.Apply)

        verify { sharedSortByViewModel.emitSortEvent(SortBy.TITLE) }
    }

    @Test
    fun `processIntent Apply with None does not call emitSortEvent`() = runTest {
        every { sharedSortByViewModel.emitSortEvent(any()) } just runs

        viewModel.processIntent(ReorderDialogIntent.Apply)

        verify(inverse = true) { sharedSortByViewModel.emitSortEvent(any()) }
    }
}