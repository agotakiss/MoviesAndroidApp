package com.agotakiss.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.agotakiss.androidtest.models.LoadMoviesResponse;
import com.agotakiss.androidtest.network.MovieDbManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieDbManager.getInstance().loadPopularMovies(1, new Callback<LoadMoviesResponse>() {
            @Override
            public void onResponse(Call<LoadMoviesResponse> call, Response<LoadMoviesResponse> response) {
                Toast.makeText(MainActivity.this, "Successful load" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoadMoviesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        MovieDbManager.getInstance().loadSimilarMovies("351286", 1, new Callback<LoadMoviesResponse>() {
            @Override
            public void onResponse(Call<LoadMoviesResponse> call, Response<LoadMoviesResponse> response) {
                Toast.makeText(MainActivity.this, "Similar movies are " + response.body().getMovies(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LoadMoviesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
