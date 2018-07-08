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
import android.widget.Toast;

import com.agotakiss.androidtest.R;
import com.agotakiss.androidtest.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class DetailedAdapter extends RecyclerView.Adapter<DetailedAdapter.detailedViewHolder> {
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342/";
    private List<Movie> movies;
    private Context context;
    private Map<Integer, String> genresMap;

    public DetailedAdapter(List<Movie> movies, Map<Integer, String> genresMap, Context context) {
        this.movies = movies;
        this.context = context;
        this.genresMap = genresMap;
    }

    public class detailedViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterImageView;
        public TextView titleTextView;
        public TextView ratingTextView;
        public TextView genresTextView;
        public TextView releaseDate;
        public TextView description;

        public detailedViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.poster_detailed);
            titleTextView = itemView.findViewById(R.id.movie_title_detailed);
            ratingTextView = itemView.findViewById(R.id.rating_detailed);
            genresTextView = itemView.findViewById(R.id.genres_detailed);
            releaseDate = itemView.findViewById(R.id.release_date_detailed);
            description = itemView.findViewById(R.id.description_detailed);
        }
    }

    @Override
    public detailedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_details, parent, false);
        return new detailedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(detailedViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.ratingTextView.setText(Float.toString(movie.getAverageVote()));
        holder.genresTextView.setText(movieGenresToDisplay(movie));
        holder.releaseDate.setText(movie.getReleaseDateText().substring(0, 4));
        holder.description.setText(movie.getOverview());
        Picasso.get().load(IMAGE_BASE_URL + movie.getPosterPath()).into(holder.posterImageView);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public String movieGenresToDisplay(Movie movie) {
        List<Integer> movieGenreIdList = movie.getGenreIdList();

        if (movie.getGenreIdList() != null && genresMap != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < movieGenreIdList.size() - 1; i++) {
                stringBuilder.append(genresMap.get(movie.getGenreIdList().get(i)));
                stringBuilder.append(", ");
            }
            stringBuilder.append(genresMap.get(movieGenreIdList.get(movieGenreIdList.size() - 1)));
            return stringBuilder.toString();
        } else return "";
    }


}
