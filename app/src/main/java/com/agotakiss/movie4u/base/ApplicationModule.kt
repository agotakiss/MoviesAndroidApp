package com.agotakiss.movie4u.base

import android.arch.persistence.room.Room
import com.agotakiss.movie4u.data.network.MovieDbApi
import com.agotakiss.movie4u.data.network.MovieRemoteStore
import com.agotakiss.movie4u.data.network.RetrofitHelper
import com.agotakiss.movie4u.data.repository.ActorRepositoryImpl
import com.agotakiss.movie4u.data.repository.CastRepositoryImpl
import com.agotakiss.movie4u.data.repository.GenreRepositoryImpl
import com.agotakiss.movie4u.data.repository.MovieRepositoryImpl
import com.agotakiss.movie4u.data.store.AppDatabase
import com.agotakiss.movie4u.data.store.FavoriteMovieDao
import com.agotakiss.movie4u.data.store.FavoriteMovieDataStore
import com.agotakiss.movie4u.data.store.GenreStore
import com.agotakiss.movie4u.data.store.GenreStoreImpl
import com.agotakiss.movie4u.domain.repository.ActorRepository
import com.agotakiss.movie4u.domain.repository.CastRepository
import com.agotakiss.movie4u.domain.repository.GenreRepository
import com.agotakiss.movie4u.domain.repository.MovieRepository
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
    fun provideMovieRepository(
        favoriteMovieDataStore: FavoriteMovieDataStore,
        movieRemoteStore: MovieRemoteStore

    ): MovieRepository {
        return MovieRepositoryImpl(favoriteMovieDataStore, movieRemoteStore)
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