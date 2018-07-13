package com.agotakiss.androidtest.presentation.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agotakiss.androidtest.R;
import com.agotakiss.androidtest.models.Movie;
import com.agotakiss.androidtest.presentation.home.OnEndReachedListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.agotakiss.androidtest.presentation.home.MainActivity.genresMap;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.BACKDROP_PATH_DETAILS;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.DESCRIPTION_DETAILS;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.GENRES_DETAILS;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.IMAGE_BASE_URL;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.LAST_ITEMS_BEFORE_LOAD_NEW;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.MOVIE_ID_DETAILS;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.MOVIE_TITLE_DETAILS;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.POSTER_PATH_DETAILS;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.RATING_DETAILS;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.RELEASE_DATE_DETAILS;

public class SimilarMovieAdapter extends RecyclerView.Adapter<SimilarMovieAdapter.ViewHolder> {
    private List<Movie> similarMovies;
    private Context context;
    OnEndReachedListener onEndReachedListener;

    public void setOnEndReachedListener(OnEndReachedListener onEndReachedListener) {
        this.onEndReachedListener = onEndReachedListener;
    }

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


            itemView.setOnClickListener(v -> {
                int itemPosition = getAdapterPosition();
                similarMovies.get(itemPosition).getId();
                String movieIdToRefresh = Integer.toString(similarMovies.get(itemPosition).getId());
                Intent intent = new Intent(context, DetailsActivity.class);

                intent.putExtra(MOVIE_ID_DETAILS, Integer.toString(similarMovies.get(itemPosition).getId()));
                intent.putExtra(POSTER_PATH_DETAILS, similarMovies.get(itemPosition).getPosterPath());
                intent.putExtra(MOVIE_TITLE_DETAILS, similarMovies.get(itemPosition).getTitle());


                List<Integer> movieGenreIdList = similarMovies.get(itemPosition).getGenreIdList();

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < movieGenreIdList.size() - 1; i++) {
                    stringBuilder.append(genresMap.get(similarMovies.get(itemPosition).getGenreIdList().get(i)));
                    stringBuilder.append(", ");
                }
                stringBuilder.append(genresMap.get(movieGenreIdList.get(movieGenreIdList.size() - 1)));


                intent.putExtra(GENRES_DETAILS, stringBuilder.toString());


                intent.putExtra(RATING_DETAILS, (Float.toString(similarMovies.get(itemPosition).getAverageVote())));
                intent.putExtra(RELEASE_DATE_DETAILS, similarMovies.get(itemPosition).getReleaseDateText());
                intent.putExtra(DESCRIPTION_DETAILS, similarMovies.get(itemPosition).getOverview());
                intent.putExtra(BACKDROP_PATH_DETAILS, similarMovies.get(itemPosition).getBackdropPath());
//                context.getActivity().finish();
                ((Activity) context).finish();
                context.startActivity(intent);
            });
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
        if (position == similarMovies.size() - LAST_ITEMS_BEFORE_LOAD_NEW){
            onEndReachedListener.onEndReached(position);
        }
        holder.similarMovieRating.setText(Float.toString(movie.getAverageVote()));
        Picasso.get().load(IMAGE_BASE_URL + movie.getPosterPath()).into(holder.similarMoviePoster);

    }

    @Override
    public int getItemCount() {
        return similarMovies.size();
    }
}
