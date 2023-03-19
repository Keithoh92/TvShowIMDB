package com.example.moviedd.data

import com.example.moviedd.BaseTest
import com.example.moviedd.common.Resource
import com.example.moviedd.data.api.dao.ApiResponse
import com.example.moviedd.data.api.remote.TVShowsApi
import com.example.moviedd.data.api.repository.TvShowApiRepositoryImpl
import com.example.moviedd.domain.api.repository.TvShowApiRepo
import com.example.moviedd.domain.extension.toShowInfo
import com.example.moviedd.domain.model.ShowInfo
import com.example.moviedd.mock.mockShowDaoList
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TvShowApiRepositoryTest : BaseTest() {

    private lateinit var tvShowApiRepo: TvShowApiRepo

    @RelaxedMockK
    private lateinit var tvShowsApi: TVShowsApi

    override fun setUp() {
        super.setUp()
        tvShowApiRepo = TvShowApiRepositoryImpl(tvShowsApi)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `downloadTvShows - emits a list of ShowInfo objects downloaded from the TMDB api`() = runTest {
        val mockApiResponse = mockk<Response<ApiResponse>>()
        every { mockApiResponse.body() } returns ApiResponse(1, mockShowDaoList(), 1, 1)
        coEvery { tvShowsApi.getTopRatedTvShows("25a8f80ba018b52efb64f05140f6b43c") } returns mockApiResponse

        val expectedShowInfoList = mockShowDaoList().map { it.toShowInfo() }

        val result = tvShowApiRepo.downloadTvShows().toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        val resourceSuccess = result[1] as Resource.Success<List<ShowInfo>>
        assertEquals(expectedShowInfoList, resourceSuccess.data)
    }
}