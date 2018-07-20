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
import com.agotakiss.androidtest.domain.models.Movie;
import com.agotakiss.androidtest.presentation.home.OnEndReachedListener;
import com.squareup.picasso.Picasso;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.agotakiss.androidtest.presentation.home.MovieAdapter.IMAGE_BASE_URL;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.LAST_ITEMS_BEFORE_LOAD_NEW;
import static com.agotakiss.androidtest.presentation.home.MovieAdapter.MOVIE;

public class SimilarMovieAdapter extends RecyclerView.Adapter<SimilarMovieAdapter.ViewHolder> {
    private List<Movie> similarMovies;
    private Context context;
    private OnEndReachedListener onEndReachedListener;

    public void setOnEndReachedListener(OnEndReachedListener onEndReachedListener) {
        this.onEndReachedListener = onEndReachedListener;
    }

    public SimilarMovieAdapter(List<Movie> similarMovies, Context context) {
        this.similarMovies = similarMovies;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.similar_movie_poster) ImageView similarMoviePoster;
        @BindView(R.id.similar_movie_rating) TextView similarMovieRating;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//            itemView.setOnClickListener(v -> {
//                int itemPosition = getAdapterPosition();
//                Movie similarMovie = similarMovies.get(itemPosition);
//                Intent intent = new Intent(context, DetailsActivity.class);
//                intent.putExtra(MOVIE, similarMovie);
//                ((Activity) context).finish();
//                context.startActivity(intent);
//            });
        }
        @OnClick(R.id.similar_movie_cardview)void onItemClicked(){
            int itemPosition = getAdapterPosition();
            Movie similarMovie = similarMovies.get(itemPosition);
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(MOVIE, similarMovie);
            ((Activity) context).finish();
            context.startActivity(intent);
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
