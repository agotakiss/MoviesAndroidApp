package com.agotakiss.androidtest.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agotakiss.androidtest.R;
import com.agotakiss.androidtest.models.Genre;
import com.agotakiss.androidtest.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342/";
    private List<Movie> movies;
    private Context context;
    private List<Genre> genres;

    public MovieAdapter(List<Movie> movies, List<Genre> genres, Context context) {
        this.movies = movies;
        this.context = context;
        this.genres = genres;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView poster;
        public TextView title;
        public TextView rating;
//        public TextView genres;
        public TextView releaseDate;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.movie_title);
            rating = itemView.findViewById(R.id.movie_rating);
//            genres = itemView.findViewById(R.id.movie_genres);
            releaseDate = itemView.findViewById(R.id.movie_release_date);
            description = itemView.findViewById(R.id.movie_description);
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
        holder.title.setText(movie.getTitle());
        holder.rating.setText(Float.toString(movie.getAverageVote()));
//        holder.genres.setText(movie.getGenres().toString());
        holder.releaseDate.setText(movie.getReleaseDateText().substring(0,4));
        holder.description.setText(movie.getOverview());
        Picasso.get().load(IMAGE_BASE_URL + movie.getPosterPath()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
