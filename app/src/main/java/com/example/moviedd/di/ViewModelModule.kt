package com.example.moviedd.di

import com.example.moviedd.data.api.remote.TVShowsApi
import com.example.moviedd.data.api.repository.TvShowApiRepositoryImpl
import com.example.moviedd.data.database.dao.TVShowDao
import com.example.moviedd.data.database.helper.AutoCompleteSearchSystem
import com.example.moviedd.data.database.repository.TvShowDBRepositoryImpl
import com.example.moviedd.domain.api.repository.TvShowApiRepo
import com.example.moviedd.domain.database.repository.TvShowDBRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideTvShowRepository(tvShowDao: TVShowDao): TvShowDBRepo {
        return TvShowDBRepositoryImpl(tvShowDao)
    }

    @Provides
    @ViewModelScoped
    fun provideTvShowApiRepo(tvShowsApi: TVShowsApi): TvShowApiRepo {
        return TvShowApiRepositoryImpl(tvShowsApi)
    }

    @Provides
    @ViewModelScoped
    fun provideAutoCompleteSystem(): AutoCompleteSearchSystem {
        return AutoCompleteSearchSystem()
    }
}