package com.example.moviedd.di

import androidx.room.Room
import com.example.moviedd.TvShowApplication
import com.example.moviedd.data.api.remote.TVShowsApi
import com.example.moviedd.data.database.TVShowDatabase
import com.example.moviedd.data.database.dao.TVShowDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(): TVShowDatabase {
        return Room.databaseBuilder(TvShowApplication.appContext, TVShowDatabase::class.java, "TVSHOWDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTvShowDao(database: TVShowDatabase): TVShowDao {
        return database.tvShowDao
    }

    @Provides
    @Singleton
    fun provideTvShowsApi(): TVShowsApi {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }).build()
            )
            .build()
            .create(TVShowsApi::class.java)
    }
}