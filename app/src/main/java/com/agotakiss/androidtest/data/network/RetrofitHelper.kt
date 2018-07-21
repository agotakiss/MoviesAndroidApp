package com.agotakiss.androidtest.data.network

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    val API_KEY = "api_key"
    val MOVIE_DB_API_KEY = "0a08e38b874d0aa2d426ffc04357069d"
    val MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/"

    private val okHttpClient: OkHttpClient
        get() {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                        .addQueryParameter(API_KEY, MOVIE_DB_API_KEY)
                        .build()

                val requestBuilder = original.newBuilder()
                        .url(url)

                val request = requestBuilder.build()
                chain.proceed(request)
            }

            return httpClient.build()
        }

    fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(MOVIE_DB_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
