package com.agotakiss.androidtest.base

import android.arch.persistence.room.Room
import com.agotakiss.androidtest.data.network.MovieDbApi
import com.agotakiss.androidtest.data.network.RetrofitHelper
import com.agotakiss.androidtest.data.repository.ActorRepositoryImpl
import com.agotakiss.androidtest.data.repository.CastRepositoryImpl
import com.agotakiss.androidtest.data.repository.GenreRepositoryImpl
import com.agotakiss.androidtest.data.repository.MovieRepositoryImpl
import com.agotakiss.androidtest.data.store.*
import com.agotakiss.androidtest.domain.repository.ActorRepository
import com.agotakiss.androidtest.domain.repository.CastRepository
import com.agotakiss.androidtest.domain.repository.GenreRepository
import com.agotakiss.androidtest.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApplicationModule(val application: MovieApplication) {

    @Provides
    @Singleton
    fun provideGenreRepository(genreStore: GenreStore, movieDbApi: MovieDbApi): GenreRepository {
        return GenreRepositoryImpl(genreStore, movieDbApi)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(favoriteMovieDataStore: FavoriteMovieDataStore, genreRepository: GenreRepository,
                               movieDbApi: MovieDbApi): MovieRepository {
        return MovieRepositoryImpl(favoriteMovieDataStore, genreRepository, movieDbApi)
    }

    @Provides
    @Singleton
    fun provideCastRepository(movieDbApi: MovieDbApi): CastRepository {
        return CastRepositoryImpl(movieDbApi)
    }

    @Provides
    @Singleton
    fun provideActorRepository(movieDbApi: MovieDbApi): ActorRepository {
        return ActorRepositoryImpl(movieDbApi)
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

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = RetrofitHelper.initRetrofit()

    @Provides
    @Singleton
    fun provideMovieDbApi(): MovieDbApi = provideRetrofit().create(MovieDbApi::class.java)

    @Provides
    @Singleton
    fun provideGenreStore(): GenreStore = GenreStoreImpl()
}