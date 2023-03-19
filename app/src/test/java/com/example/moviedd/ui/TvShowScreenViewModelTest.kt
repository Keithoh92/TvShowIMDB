package com.example.moviedd.ui

import com.example.moviedd.BaseTest
import com.example.moviedd.domain.api.repository.TvShowApiRepo
import com.example.moviedd.domain.database.repository.TvShowDBRepo
import com.example.moviedd.domain.model.ShowInfo
import com.example.moviedd.mock.mockShowInfoList
import com.example.moviedd.ui.tv.event.TvShowScreenEvent
import com.example.moviedd.ui.tv.state.TvShowScreenUIState
import com.example.moviedd.ui.tv.viewModel.TvShowScreenViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TvShowScreenViewModelTest : BaseTest() {

    private lateinit var target: TvShowScreenViewModel

    private val tvShowScreenUIState = mockk<TvShowScreenUIState>(relaxed = true)

    @RelaxedMockK
    private lateinit var tvShowDbRepo: TvShowDBRepo

    @RelaxedMockK
    private lateinit var tvShowApiRepo: TvShowApiRepo

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun setUp() {
        super.setUp()
        Dispatchers.setMain(TestCoroutineDispatcher())
        target = TvShowScreenViewModel(tvShowDbRepo, tvShowApiRepo)

        val showInfoList = mockk<List<ShowInfo>>()

        every { showInfoList.first().title } returns mockShowInfoList().first().title
        every { showInfoList.first().imagePath } returns mockShowInfoList().first().imagePath
        every { showInfoList.first().airDate } returns mockShowInfoList().first().airDate
        every { showInfoList.first().rating } returns mockShowInfoList().first().rating
        every { showInfoList.first().description } returns mockShowInfoList().first().description

        every { showInfoList[1].title } returns mockShowInfoList()[1].title
        every { showInfoList[1].imagePath } returns mockShowInfoList()[1].imagePath
        every { showInfoList[1].airDate } returns mockShowInfoList()[1].airDate
        every { showInfoList[1].rating } returns mockShowInfoList()[1].rating
        every { showInfoList[1].description } returns mockShowInfoList()[1].description
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onRefresh - downloads the tvShows from API and saves the tvShows in the DB and updates the UIState`() = runTest {
        coEvery { tvShowDbRepo.getTvShows() } returns mockShowInfoList()

        target.onEvent(TvShowScreenEvent.OnRefresh)

        coVerify { tvShowApiRepo.downloadTvShows() }
        coVerify { tvShowDbRepo.getTvShows() }

        val result = target.tvShowScreenUIState.tvShowList

        assertEquals(2, result.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSortOptionChosen - when user chooses sort option - sort by - Alphabetically - sort the tvShowList in alphabetical order`() = runTest {
        coEvery { tvShowDbRepo.getTvShows() } returns mockShowInfoList()

        target.onEvent(TvShowScreenEvent.OnRefresh)
        val resultBeforeSorting = target.tvShowScreenUIState.tvShowList

        target.onEvent(TvShowScreenEvent.OnSortOptionChosen("Alphabetically"))

        coVerify { tvShowDbRepo.getTvShows() }

        val resultAfterSorting = target.tvShowScreenUIState.tvShowList

        assertEquals("C TvShow", resultBeforeSorting.first().title)
        assertEquals("A TvShow", resultAfterSorting.first().title)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSortOptionChosen - when user chooses sort option - sort by - Air Date - sort the tvShowList by the air date`() = runTest {
        coEvery { tvShowDbRepo.getTvShows() } returns mockShowInfoList()

        target.onEvent(TvShowScreenEvent.OnRefresh)
        val resultBeforeSorting = target.tvShowScreenUIState.tvShowList

        target.onEvent(TvShowScreenEvent.OnSortOptionChosen("Air Date"))

        coVerify { tvShowDbRepo.getTvShows() }

        val resultAfterSorting = target.tvShowScreenUIState.tvShowList

        assertEquals("10-01-22", resultBeforeSorting.first().airDate)
        assertEquals("09-01-22", resultAfterSorting.first().airDate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onSortOptionChosen - when user chooses sort option - sort by - Top Rated - sort the tvShowList by the air date`() = runTest {
        coEvery { tvShowDbRepo.getTvShows() } returns mockShowInfoList()

        target.onEvent(TvShowScreenEvent.OnRefresh)
        val resultBeforeSorting = target.tvShowScreenUIState.tvShowList

        target.onEvent(TvShowScreenEvent.OnSortOptionChosen("Top Rated"))

        coVerify { tvShowDbRepo.getTvShows() }

        val resultAfterSorting = target.tvShowScreenUIState.tvShowList

        assertEquals(8.9, resultBeforeSorting.first().rating)
        assertEquals(9.9, resultAfterSorting.first().rating)
    }

    @Test
    fun `updateCardLayout - toggles the state of isGridView`() {
        // before
        val isGridViewBefore = target.tvShowScreenUIState.isGridView

        target.onEvent(TvShowScreenEvent.OnViewChanged(false))

        // after
        val isGridViewAfter = target.tvShowScreenUIState.isGridView

        assertEquals(false, isGridViewBefore)
        assertEquals(true, isGridViewAfter)
    }
}