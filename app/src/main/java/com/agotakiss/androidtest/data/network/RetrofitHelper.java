package com.agotakiss.androidtest.data.network;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static final String API_KEY = "api_key";
    public static final String MOVIE_DB_API_KEY = "0a08e38b874d0aa2d426ffc04357069d";
    public static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/";

    public static Retrofit initRetrofit() {
        return new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(MOVIE_DB_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter(API_KEY, MOVIE_DB_API_KEY)
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        return httpClient.build();
    }
}
