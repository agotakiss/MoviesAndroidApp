package com.agotakiss.androidtest.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agotakiss.androidtest.R;
import com.agotakiss.androidtest.models.LoadMoviesResponse;
import com.agotakiss.androidtest.models.Movie;
import com.agotakiss.androidtest.network.MovieDbManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.agotakiss.androidtest.presentation.MovieAdapter.BACKDROP_PATH_DETAILS;
import static com.agotakiss.androidtest.presentation.MovieAdapter.DESCRIPTION_DETAILS;
import static com.agotakiss.androidtest.presentation.MovieAdapter.GENRES_DETAILS;
import static com.agotakiss.androidtest.presentation.MovieAdapter.GENRES_MAP;
import static com.agotakiss.androidtest.presentation.MovieAdapter.IMAGE_BASE_URL;
import static com.agotakiss.androidtest.presentation.MovieAdapter.MOVIE_ID_DETAILS;
import static com.agotakiss.androidtest.presentation.MovieAdapter.MOVIE_TITLE_DETAILS;
import static com.agotakiss.androidtest.presentation.MovieAdapter.POSTER_PATH_DETAILS;
import static com.agotakiss.androidtest.presentation.MovieAdapter.RATING_DETAILS;
import static com.agotakiss.androidtest.presentation.MovieAdapter.RELEASE_DATE_DETAILS;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String movieId = initUI();



        MovieDbManager.getInstance().loadSimilarMovies(movieId, 1, new Callback<LoadMoviesResponse>() {
            @Override
            public void onResponse(Call<LoadMoviesResponse> call, Response<LoadMoviesResponse> response) {
                displaySimilarMoviesInRecyclerView(response.body().getMovies());
            }

            @Override
            public void onFailure(Call<LoadMoviesResponse> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private String initUI() {
        String movieId = getIntent().getStringExtra(MOVIE_ID_DETAILS);
        String posterDetails = getIntent().getStringExtra(POSTER_PATH_DETAILS);
        String movieTitleDetails = getIntent().getStringExtra(MOVIE_TITLE_DETAILS);
        String genresDetails = getIntent().getStringExtra(GENRES_DETAILS);
        String ratingDetails = getIntent().getStringExtra(RATING_DETAILS);
        String releaseDateDetails = getIntent().getStringExtra(RELEASE_DATE_DETAILS);
        String descriptionDetails = getIntent().getStringExtra(DESCRIPTION_DETAILS);
        String backdropPathDetails = getIntent().getStringExtra(BACKDROP_PATH_DETAILS);


        ImageView posterDetailsImageView = findViewById(R.id.poster_detailed);
        TextView movieTitleDetailsTextView = findViewById(R.id.movie_title_detailed);
        TextView genresDetailedTextView = findViewById(R.id.genres_detailed);
        TextView ratingDetailedTextView = findViewById(R.id.rating_detailed);
        TextView releaseDateDetailedTextView = findViewById(R.id.release_date_detailed);
        TextView descriptionDetailedTextView = findViewById(R.id.description_detailed);
        ImageView backdropDetailedImageView = findViewById(R.id.backdrop_detailed);

        Picasso.get().load(IMAGE_BASE_URL + posterDetails).into(posterDetailsImageView);
        Picasso.get().load(IMAGE_BASE_URL + backdropPathDetails).into(backdropDetailedImageView);
        backdropDetailedImageView.setAlpha(0.2f);

        movieTitleDetailsTextView.setText(movieTitleDetails);
        genresDetailedTextView.setText(genresDetails);
        ratingDetailedTextView.setText(ratingDetails);
        releaseDateDetailedTextView.setText(releaseDateDetails);
        descriptionDetailedTextView.setText(descriptionDetails);
        descriptionDetailedTextView.setMovementMethod(new ScrollingMovementMethod());
        return movieId;
    }

    public void displaySimilarMoviesInRecyclerView(List<Movie> results) {
//        HashMap<Integer, String> genresHashMap =(HashMap<Integer, String>) getIntent().getSerializableExtra(GENRES_DETAILS);

        RecyclerView similarMoviesList = findViewById(R.id.similar_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        similarMoviesList.setLayoutManager(layoutManager);
        SimilarMovieAdapter adapter = new SimilarMovieAdapter(results, DetailsActivity.this);
        similarMoviesList.setAdapter(adapter);
    }

}
