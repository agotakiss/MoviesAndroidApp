package com.agotakiss.androidtest.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.agotakiss.androidtest.R;
import com.agotakiss.androidtest.models.LoadGenresResponse;
import com.agotakiss.androidtest.models.LoadMoviesResponse;
import com.agotakiss.androidtest.models.Movie;
import com.agotakiss.androidtest.network.MovieDbManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    private RecyclerView moviesList;
    private MovieAdapter adapter;
    private Map<Integer, String> genresMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MovieDbManager.getInstance().loadPopularMovies(1, new Callback<LoadMoviesResponse>() {
            @Override
            public void onResponse(Call<LoadMoviesResponse> call, Response<LoadMoviesResponse> response) {
                displayInRecyclerView(response.body().getMovies());
            }

            @Override
            public void onFailure(Call<LoadMoviesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        MovieDbManager.getInstance().loadSimilarMovies("351286", 1, new Callback<LoadMoviesResponse>() {
            @Override
            public void onResponse(Call<LoadMoviesResponse> call, Response<LoadMoviesResponse> response) {
//                Toast.makeText(MainActivity.this, "Similar movies are " + response.body().getMovies(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LoadMoviesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        MovieDbManager.getInstance().loadGenres(new Callback<LoadGenresResponse>() {
            @Override
            public void onResponse(Call<LoadGenresResponse> call, Response<LoadGenresResponse> response) {

                for (int i = 0; i < response.body().getGenres().size(); i++) {
                    genresMap.put(response.body().getGenres().get(i).getId(), response.body().getGenres().get(i).getName());
                }
            }

            @Override
            public void onFailure(Call<LoadGenresResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void displayInRecyclerView(List<Movie> results) {
        moviesList = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        moviesList.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(results, genresMap, MainActivity.this);
        moviesList.setAdapter(adapter);
    }
}
