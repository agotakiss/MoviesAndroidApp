package com.agotakiss.androidtest.presentation.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.agotakiss.androidtest.R;
import com.agotakiss.androidtest.models.Movie;
import com.agotakiss.androidtest.network.MovieDbManager;
import com.agotakiss.androidtest.presentation.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    public static HashMap<Integer, String> genresMap = new HashMap<>();
    private int page = 1;
    private int totalPages;
    List<Movie> movieList = new ArrayList<>();
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
        adapter = new MovieAdapter(movieList, MainActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.setOnEndReachedListener(position -> {
            page++;
            if (page < totalPages) {
                loadNextPopularMoviesPage();
            }
        });
    }

    private void loadNextPopularMoviesPage() {
//        MovieDbManager.getInstance().loadPopularMovies(page, new Callback<LoadMoviesResponse>() {
//            @Override
//            public void onResponse(Call<LoadMoviesResponse> call, Response<LoadMoviesResponse> response) {
//                onMoviesLoaded(response.body().getMovies(), response.body().getTotalPages());
//            }
//
//            @Override
//            public void onFailure(Call<LoadMoviesResponse> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        MovieDbManager.getInstance().loadPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieList -> {
                    onMoviesLoaded(movieList, Integer.MAX_VALUE);
                }, throwable -> {
                    logE(throwable);
                    Toast.makeText(MainActivity.this, "Error: " + throwable.toString(), Toast.LENGTH_SHORT).show();
                });
    }

    private void onMoviesLoaded(List<Movie> newMovies, int pages) {
        movieList.addAll(newMovies);
        adapter.notifyItemRangeInserted(movieList.size() - newMovies.size(), newMovies.size());
        totalPages = pages;
    }

    public void loadGenres() {
//        MovieDbManager.getInstance().loadGenres(new Callback<LoadGenresResponse>() {
//            @Override
//            public void onResponse(Call<LoadGenresResponse> call, Response<LoadGenresResponse> response) {
//                for (int i = 0; i < response.body().getGenres().size(); i++) {
//                    genresMap.put(response.body().getGenres().get(i).getId(), response.body().getGenres().get(i).getName());
//                }
//            }
//            @Override
//            public void onFailure(Call<LoadGenresResponse> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        MovieDbManager.getInstance().loadGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(genres -> {
                    for (int i = 0; i < genres.size(); i++) {
                        genresMap.put(genres.get(i).getId(), genres.get(i).getName());
                    }
                }, throwable -> {
                    logE(throwable);
                    Toast.makeText(MainActivity.this, "Error: " + throwable.toString(), Toast.LENGTH_SHORT).show();
                });
    }
}
