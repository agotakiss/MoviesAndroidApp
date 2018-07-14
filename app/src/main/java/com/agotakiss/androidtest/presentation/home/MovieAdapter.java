package com.agotakiss.androidtest.presentation.home;

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
import com.agotakiss.androidtest.data.models.Movie;
import com.agotakiss.androidtest.presentation.detail.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.agotakiss.androidtest.presentation.home.MainActivity.genresMap;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342/";
    public static final int LAST_ITEMS_BEFORE_LOAD_NEW = 5;
    public static final String MOVIE = "movie";
    private List<Movie> movies;
    private Context context;
    private OnEndReachedListener onEndReachedListener;

    public void setOnEndReachedListener(OnEndReachedListener onEndReachedListener) {
        this.onEndReachedListener = onEndReachedListener;
    }

    public MovieAdapter(List<Movie> movies,  Context context) {
        this.movies = movies;
        this.context = context;
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
        if (position == movies.size() - 1){
            onEndReachedListener.onEndReached(position);
        }
        holder.titleTextView.setText(movie.getTitle());
        holder.ratingTextView.setText(Float.toString(movie.getAverageVote()));
        holder.genresTextView.setText(movieGenresToDisplay(movie));
        holder.releaseDate.setText(movie.getReleaseDateText().substring(0, 4));
        holder.description.setText(movie.getOverview());
        Picasso.get().load(IMAGE_BASE_URL + movie.getPosterPath()).into(holder.posterImageView);

        holder.moreInfoButton.setOnClickListener(v -> {
             Intent intent = new Intent(context, DetailsActivity.class);
             intent.putExtra(MOVIE, movie);
            context.startActivity(intent);
        });
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
