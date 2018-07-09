package com.agotakiss.androidtest.presentation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.agotakiss.androidtest.R;
import com.agotakiss.androidtest.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342/";
    public static final String MOVIE_ID_DETAILS = "movie_id_details";
    public static final String POSTER_PATH_DETAILS = "poster_path_details";
    public static final String GENRES_DETAILS = "genres_details";
    public static final String RATING_DETAILS = "rating_details";
    public static final String RELEASE_DATE_DETAILS = "release_date_details";
    public static final String DESCRIPTION_DETAILS = "description_details";
    public static final String BACKDROP_PATH_DETAILS = "backdrop_path_details";
    public static final String MOVIE_TITLE_DETAILS = "movie_title_details";
    public static final String GENRES_MAP = "genres_map";
    private List<Movie> movies;
    private Context context;
    private HashMap<Integer, String> genresMapInMovieAdapter;

    public MovieAdapter(List<Movie> movies, HashMap<Integer, String> genresMapInMovieAdapter, Context context) {
        this.movies = movies;
        this.context = context;
        this.genresMapInMovieAdapter = genresMapInMovieAdapter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterImageView;
        public TextView titleTextView;
        public TextView ratingTextView;
        public TextView genresTextView;
        public TextView releaseDate;
        public TextView description;
        public Button moreInfoButton;

        public ViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.poster);
            titleTextView = itemView.findViewById(R.id.movie_title);
            ratingTextView = itemView.findViewById(R.id.movie_rating);
            genresTextView = itemView.findViewById(R.id.movie_genres);
            releaseDate = itemView.findViewById(R.id.movie_release_date);
            description = itemView.findViewById(R.id.movie_description);
            moreInfoButton = itemView.findViewById(R.id.more_info_button);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.ratingTextView.setText(Float.toString(movie.getAverageVote()));
        holder.genresTextView.setText(movieGenresToDisplay(movie));
        holder.releaseDate.setText(movie.getReleaseDateText().substring(0, 4));
        holder.description.setText(movie.getOverview());
        Picasso.get().load(IMAGE_BASE_URL + movie.getPosterPath()).into(holder.posterImageView);

        holder.moreInfoButton.setOnClickListener(v -> {
             Intent intent = new Intent(context, DetailsActivity.class);
             intent.putExtra(MOVIE_ID_DETAILS, Integer.toString(movie.getId()));
             intent.putExtra(POSTER_PATH_DETAILS, movie.getPosterPath());
             intent.putExtra(MOVIE_TITLE_DETAILS, movie.getTitle());
             intent.putExtra(GENRES_DETAILS, movieGenresToDisplay(movie));
             intent.putExtra(RATING_DETAILS, (Float.toString(movie.getAverageVote())));
             intent.putExtra(RELEASE_DATE_DETAILS, movie.getReleaseDateText());
             intent.putExtra(DESCRIPTION_DETAILS, movie.getOverview());
             intent.putExtra(BACKDROP_PATH_DETAILS, movie.getBackdropPath());
             intent.putExtra(GENRES_MAP, genresMapInMovieAdapter);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public String movieGenresToDisplay(Movie movie) {
        List<Integer> movieGenreIdList = movie.getGenreIdList();

        if (movie.getGenreIdList() != null && genresMapInMovieAdapter != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < movieGenreIdList.size() - 1; i++) {
                stringBuilder.append(genresMapInMovieAdapter.get(movie.getGenreIdList().get(i)));
                stringBuilder.append(", ");
            }
            stringBuilder.append(genresMapInMovieAdapter.get(movieGenreIdList.get(movieGenreIdList.size() - 1)));
            return stringBuilder.toString();
        } else return "";
    }


}
