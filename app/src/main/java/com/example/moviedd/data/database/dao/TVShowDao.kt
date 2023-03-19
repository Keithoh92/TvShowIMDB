package com.example.moviedd.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviedd.data.database.entity.TVShow

@Dao
interface TVShowDao {

    @Query("SELECT * FROM tv_shows")
    fun getAllTvShows(): List<TVShow>

    @Query("SELECT count(title) FROM tv_shows WHERE title = :title")
    fun checkExists(title: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(showInfo: TVShow)
}