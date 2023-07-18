package com.example.moviedd.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviedd.data.database.dao.TVShowDao
import com.example.moviedd.data.database.entity.TVShow

@Database(entities = [TVShow::class], version = 2, exportSchema = false)
abstract class TVShowDatabase: RoomDatabase() {

    abstract val tvShowDao: TVShowDao

    companion object {
        @Volatile
        private var INSTANCE: TVShowDatabase? = null

        fun getDatabase(context: Context): TVShowDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TVShowDatabase::class.java,
                    "tv_show_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}