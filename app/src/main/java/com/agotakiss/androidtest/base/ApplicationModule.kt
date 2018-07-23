package com.agotakiss.androidtest.base

import com.agotakiss.androidtest.data.repository.GenreRepositoryImpl
import com.agotakiss.androidtest.data.repository.MovieRepositoryImpl
import com.agotakiss.androidtest.domain.repository.GenreRepository
import com.agotakiss.androidtest.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule() {

    @Provides
    @Singleton
    fun provideGenreRepository(): GenreRepository {
        return GenreRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(): MovieRepository {
        return MovieRepositoryImpl()
    }
}