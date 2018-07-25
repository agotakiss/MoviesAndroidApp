package com.agotakiss.androidtest.base

import com.agotakiss.androidtest.data.repository.ActorRepositoryImpl
import com.agotakiss.androidtest.data.repository.CastRepositoryImpl
import com.agotakiss.androidtest.data.repository.GenreRepositoryImpl
import com.agotakiss.androidtest.data.repository.MovieRepositoryImpl
import com.agotakiss.androidtest.domain.repository.ActorRepository
import com.agotakiss.androidtest.domain.repository.CastRepository
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
    @Provides
    @Singleton
    fun provideCastRepository(): CastRepository {
        return CastRepositoryImpl()
    }
    @Provides
    @Singleton
    fun provideActorRepository(): ActorRepository {
        return ActorRepositoryImpl()
    }
}