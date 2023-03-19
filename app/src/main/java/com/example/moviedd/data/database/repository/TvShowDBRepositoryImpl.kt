package com.example.moviedd.data.database.repository

import com.example.moviedd.data.database.dao.TVShowDao
import com.example.moviedd.domain.model.ShowInfo
import com.example.moviedd.domain.database.repository.TvShowDBRepo
import com.example.moviedd.domain.extension.toShowInfo
import com.example.moviedd.domain.extension.toTVShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TvShowDBRepositoryImpl(private val tvShowDao: TVShowDao): TvShowDBRepo {

    override suspend fun getTvShows(): List<ShowInfo> = withContext(Dispatchers.IO) {
        val response = tvShowDao.getAllTvShows()
        return@withContext response.map { it.toShowInfo() }
    }

    override suspend fun insert(showInfo: ShowInfo) = withContext(Dispatchers.IO) {
        if (!checkExists(showInfo.title)) {
            return@withContext tvShowDao.insert(showInfo.toTVShow())
        }
    }

    private suspend fun checkExists(title: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext tvShowDao.checkExists(title) > 0
    }
}