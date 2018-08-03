package com.agotakiss.androidtest.base

import android.arch.persistence.room.Room
import com.agotakiss.androidtest.data.repository.ActorRepositoryImpl
import com.agotakiss.androidtest.data.repository.CastRepositoryImpl
import com.agotakiss.androidtest.data.repository.GenreRepositoryImpl
import com.agotakiss.androidtest.data.repository.MovieRepositoryImpl
import com.agotakiss.androidtest.data.store.AppDatabase
import com.agotakiss.androidtest.data.store.FavoriteMovieDao
import com.agotakiss.androidtest.data.store.FavoriteMovieDataStore
import com.agotakiss.androidtest.domain.repository.ActorRepository
import com.agotakiss.androidtest.domain.repository.CastRepository
import com.agotakiss.androidtest.domain.repository.GenreRepository
import com.agotakiss.androidtest.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val application: MovieApplication) {

    @Provides
    @Singleton
    fun provideGenreRepository(): GenreRepository {
        return GenreRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(favoriteMovieDataStore: FavoriteMovieDataStore): MovieRepository {
        return MovieRepositoryImpl(favoriteMovieDataStore)
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

    @Provides
    @Singleton
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(application,
            AppDatabase::class.java, "movies_database")
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: AppDatabase): FavoriteMovieDao =
        database.favoriteMovieDao()
}