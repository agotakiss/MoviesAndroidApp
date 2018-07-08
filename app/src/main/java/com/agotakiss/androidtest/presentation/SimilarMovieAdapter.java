package com.agotakiss.androidtest.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agotakiss.androidtest.R;
import com.agotakiss.androidtest.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.agotakiss.androidtest.presentation.MovieAdapter.IMAGE_BASE_URL;

public class SimilarMovieAdapter extends RecyclerView.Adapter<SimilarMovieAdapter.ViewHolder> {
    private List<Movie> similarMovies;
    private Context context;

    public SimilarMovieAdapter(List<Movie> similarMovies, Context context) {
        this.similarMovies = similarMovies;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView similarMoviePoster;
        private TextView similarMovieRating;

        public ViewHolder(View itemView) {
            super(itemView);
            similarMovieRating = itemView.findViewById(R.id.similar_movie_rating);
            similarMoviePoster = itemView.findViewById(R.id.similar_movie_poster);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = similarMovies.get(position);
        holder.similarMovieRating.setText(Float.toString(movie.getAverageVote()));
        Picasso.get().load(IMAGE_BASE_URL + movie.getPosterPath()).into(holder.similarMoviePoster);

    }

    @Override
    public int getItemCount() {
        return similarMovies.size();
    }
}
