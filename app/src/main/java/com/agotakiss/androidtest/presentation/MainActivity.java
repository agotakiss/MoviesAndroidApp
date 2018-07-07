package com.agotakiss.androidtest.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.agotakiss.androidtest.R;
import com.agotakiss.androidtest.models.LoadMoviesResponse;
import com.agotakiss.androidtest.models.Movie;
import com.agotakiss.androidtest.network.MovieDbManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    private RecyclerView moviesList;
    private MovieAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
//    private List<Movie> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ImageView ratingStar = findViewById(R.id.rating_star);
//        ratingStar.setImageResource(R.drawable.star);
//        ImageView calendar = findViewById(R.id.calendar);
//        calendar.setImageResource(R.drawable.calendar);



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

    }
    public void displayInRecyclerView(List<Movie> results){
        moviesList = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        moviesList.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(results, MainActivity.this);
        moviesList.setAdapter(adapter);
    }
}
