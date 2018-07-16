package com.agotakiss.androidtest.presentation.detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agotakiss.androidtest.R;
import com.agotakiss.androidtest.domain.models.Genre;
import com.agotakiss.androidtest.domain.models.Movie;
import com.agotakiss.androidtest.domain.repository.MovieRepository;
import com.agotakiss.androidtest.injector.Injector;
import com.agotakiss.androidtest.presentation.BaseActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

import static com.agotakiss.androidtest.presentation.home.MovieAdapter.IMAGE_BASE_URL;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.MOVIE;

public class DetailsActivity extends BaseActivity {

    private MovieRepository movieRepository = Injector.getMovieRepository();

    private int similarMoviesPage = 1;
    private int totalPages;
    private String movieId;
    private Movie movie;
    List<Movie> similarMovieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SimilarMovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        movie = (Movie) getIntent().getSerializableExtra(MOVIE);
        movieId = Integer.toString(movie.getId());
        initUI();
        loadSimilarMovies();
        initializeSimilarMovieList();
    }

    private void loadSimilarMovies() {
//        MovieDbManager.getInstance().loadSimilarMovies(movieId, similarMoviesPage, new Callback<LoadMoviesResponse>() {
//            @Override
//            public void onResponse(Call<LoadMoviesResponse> call, Response<LoadMoviesResponse> response) {
//               onSimilarMoviesLoaded(response.body().getMovies(), response.body().getTotalPages());
//            }
//
//            @Override
//            public void onFailure(Call<LoadMoviesResponse> call, Throwable t) {
//                Toast.makeText(DetailsActivity.this, "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
        movieRepository.getSimilarMovies(movieId, similarMoviesPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieList -> {
                    onSimilarMoviesLoaded(movieList, Integer.MAX_VALUE);
                }, throwable -> {
                    logE(throwable);
//                    Toast.makeText(DetailsActivity.this, "Error: " + throwable.toString(), Toast.LENGTH_SHORT).show();
                });
    }

    private void initUI() {
        ImageView posterDetailsImageView = findViewById(R.id.poster_detailed);
        TextView movieTitleDetailsTextView = findViewById(R.id.movie_title_detailed);
        TextView genresDetailedTextView = findViewById(R.id.genres_detailed);
        TextView ratingDetailedTextView = findViewById(R.id.rating_detailed);
        TextView releaseDateDetailedTextView = findViewById(R.id.release_date_detailed);
        TextView descriptionDetailedTextView = findViewById(R.id.description_detailed);
        ImageView backdropDetailedImageView = findViewById(R.id.backdrop_detailed);

        Picasso.get().load(IMAGE_BASE_URL + movie.getPosterPath()).into(posterDetailsImageView);
        Picasso.get().load(IMAGE_BASE_URL + movie.getBackdropPath()).into(backdropDetailedImageView);
        backdropDetailedImageView.setAlpha(0.2f);

        movieTitleDetailsTextView.setText(movie.getTitle());
        genresDetailedTextView.setText(genresToString());
        ratingDetailedTextView.setText(Float.toString(movie.getAverageVote()));
        releaseDateDetailedTextView.setText(movie.getReleaseDateText().substring(0, 4));
        descriptionDetailedTextView.setText(movie.getOverview());
        descriptionDetailedTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    private void onSimilarMoviesLoaded(List<Movie> newMovies, int pages) {
        similarMovieList.addAll(newMovies);
        adapter.notifyItemRangeInserted(similarMovieList.size() - newMovies.size(), newMovies.size());
        totalPages = pages;
    }

    public void initializeSimilarMovieList() {
        recyclerView = findViewById(R.id.similar_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SimilarMovieAdapter(similarMovieList, DetailsActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.setOnEndReachedListener(position -> {
            similarMoviesPage++;
            if (similarMoviesPage < totalPages) {
                loadSimilarMovies();
            }
        });
    }

    public String genresToString() {
//        List<Integer> movieGenreIdList = movie.getGenreIdList();
//        if (movie.getGenreIdList() != null && genresMap != null) {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i = 0; i < movie.getGenreIdList().size() - 1; i++) {
//                stringBuilder.append(genresMap.get(movieGenreIdList.get(i)));
//                stringBuilder.append(", ");
//            }
//            stringBuilder.append(genresMap.get(movieGenreIdList.get(movieGenreIdList.size() - 1)));
//            return stringBuilder.toString();
//        } else return "";
        if(movie.getGenres() != null && !movie.getGenres().isEmpty()){
            return Observable.fromIterable(movie.getGenres())
                    .map(Genre::getName)
                    .reduce((s, s2) -> s + ", " + s2).blockingGet();
        } else{
            return "";
        }
    }

}
