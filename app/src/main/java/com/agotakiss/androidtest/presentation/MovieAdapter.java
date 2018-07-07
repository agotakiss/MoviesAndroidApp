package com.agotakiss.androidtest.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agotakiss.androidtest.R;
import com.agotakiss.androidtest.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342/";
    private List<Movie> movies;
    private Context context;
    private Map<Integer, String> genresMap;

    public MovieAdapter(List<Movie> movies, Map<Integer, String> genresMap, Context context) {
        this.movies = movies;
        this.context = context;
        this.genresMap = genresMap;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterImageView;
        public TextView titleTextView;
        public TextView ratingTextView;
        public TextView genresTextView;
        public TextView releaseDate;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.poster);
            titleTextView = itemView.findViewById(R.id.movie_title);
            ratingTextView = itemView.findViewById(R.id.movie_rating);
            genresTextView = itemView.findViewById(R.id.movie_genres);
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
        holder.titleTextView.setText(movie.getTitle());
        holder.ratingTextView.setText(Float.toString(movie.getAverageVote()));
        holder.genresTextView.setText(movieGenresToDisplay(movie));

//        if (movie.getGenreIdList() != null && genresMap != null) {
//            holder.genresTextView.setText(genresMap.get(movie.getGenreIdList().get(2)));
//        } else holder.genresTextView.setText("");
        holder.releaseDate.setText(movie.getReleaseDateText().substring(0, 4));
        holder.description.setText(movie.getOverview());
        Picasso.get().load(IMAGE_BASE_URL + movie.getPosterPath()).into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public String movieGenresToDisplay(Movie movie) {
        if (movie.getGenreIdList() != null && genresMap != null) {
            StringBuilder genresToDisplay = new StringBuilder();

            List<String> genresToDisplayInList = new ArrayList<>();
//            Toast.makeText(context, genresMap.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(context, movie.getGenreIdList().toString(), Toast.LENGTH_LONG).show();
            for (int i = 0; i < movie.getGenreIdList().size(); i++) {
                genresToDisplayInList.add(genresMap.get(movie.getGenreIdList().get(i)));
            }
            return genresToDisplayInList.toString();
//            holder.genresTextView.setText(genresMap.get(movie.getGenreIdList().get(2)));
        } else return "";
    }


}
