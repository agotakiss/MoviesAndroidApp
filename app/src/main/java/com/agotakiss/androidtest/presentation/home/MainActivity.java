package com.agotakiss.androidtest.presentation.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.agotakiss.androidtest.R;
import com.agotakiss.androidtest.models.LoadGenresResponse;
import com.agotakiss.androidtest.models.LoadMoviesResponse;
import com.agotakiss.androidtest.models.Movie;
import com.agotakiss.androidtest.network.MovieDbManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    public static HashMap<Integer, String> genresMap = new HashMap<>();
    private int page = 1;
    List<Movie> movieList = new ArrayList<>();
    final Handler handler = new Handler();

    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeList();
        loadNextPopularMoviesPage();
        loadGenres();
    }

    private void initializeList() {
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(movieList, genresMap, MainActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.setOnEndReachedListener(position -> {
            page++;
            loadNextPopularMoviesPage();
        });
    }

    private void loadNextPopularMoviesPage() {
        MovieDbManager.getInstance().loadPopularMovies(page, new Callback<LoadMoviesResponse>() {
            @Override
            public void onResponse(Call<LoadMoviesResponse> call, Response<LoadMoviesResponse> response) {
                onMoviesLoaded(response.body().getMovies());
            }

            @Override
            public void onFailure(Call<LoadMoviesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onMoviesLoaded(List<Movie> newMovies){
        movieList.addAll(newMovies);
        adapter.notifyItemRangeInserted(movieList.size()-newMovies.size(), newMovies.size());
    }


    public void loadGenres() {
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


}
