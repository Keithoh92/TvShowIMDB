package com.example.moviedd.data

import com.example.moviedd.BaseTest
import com.example.moviedd.data.database.dao.TVShowDao
import com.example.moviedd.data.database.repository.TvShowDBRepositoryImpl
import com.example.moviedd.domain.database.repository.TvShowDBRepo
import com.example.moviedd.domain.model.ShowInfo
import com.example.moviedd.mock.mockTvShowList
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TvShowDbRepositoryTest : BaseTest() {

    private lateinit var tvShowDBRepo: TvShowDBRepo

    @RelaxedMockK
    private lateinit var tvShowDao: TVShowDao

    private val mockShowInfo = ShowInfo(
        id = 0,
        title = "Testing TvShow 0",
        imagePath = "pathToImage0",
        description = "Tv show description 0",
        rating = 7.5,
        airDate = "10-02-2023"
    )

    override fun setUp() {
        super.setUp()
        tvShowDBRepo = TvShowDBRepositoryImpl(tvShowDao)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getTvShows() - returns a list of TvShows from TvShows table`() = runTest {
        every { tvShowDao.getAllTvShows() } returns mockTvShowList()

        val result = tvShowDBRepo.getTvShows()

        verify { tvShowDao.getAllTvShows() }

        assertEquals(4, result.size)
        assertEquals("Testing TvShow 1", result[1].title)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getTvShows() - returns a emptyList when nothing is in the DB`() = runTest {
        every { tvShowDao.getAllTvShows() } returns emptyList()

        val result = tvShowDBRepo.getTvShows()

        verify { tvShowDao.getAllTvShows() }

        assertEquals(0, result.size)
        assertEquals(emptyList(), result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `insert() - does insert a tvShow when one doesn't already exist`() = runTest {
        val showInfo = mockk<ShowInfo>()
        every { showInfo.title } returns mockShowInfo.title
        every { showInfo.imagePath } returns mockShowInfo.imagePath
        every { showInfo.airDate } returns mockShowInfo.airDate
        every { showInfo.rating } returns mockShowInfo.rating
        every { showInfo.description } returns mockShowInfo.description

        every { tvShowDao.checkExists(mockShowInfo.title) } returns 0
        every { tvShowDao.insert(mockTvShowList().first()) } returns Unit

        tvShowDBRepo.insert(showInfo)

        verify { tvShowDao.checkExists(mockShowInfo.title) }
        verify { tvShowDao.insert(mockTvShowList().first()) }

        verify(exactly = 1) { tvShowDao.insert(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `insert() - does not insert a tvShow when one already exists`() = runTest {
        val showInfo = mockk<ShowInfo>()
        every { showInfo.title } returns mockShowInfo.title
        every { tvShowDao.checkExists(mockShowInfo.title) } returns 1

        tvShowDBRepo.insert(showInfo)

        verify { tvShowDao.checkExists(mockShowInfo.title) }
        verify(exactly = 0) { tvShowDao.insert(any()) }
    }
}