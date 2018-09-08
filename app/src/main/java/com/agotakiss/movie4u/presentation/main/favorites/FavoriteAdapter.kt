package com.agotakiss.movie4u.presentation.main.favorites

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.movie4u.R
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.presentation.POSTER_TRANSITION_NAME
import com.agotakiss.movie4u.presentation.main.OnEndReachedListener
import com.agotakiss.movie4u.presentation.main.PopularMovieAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.favorites_list_item.view.favorite_movie_poster_iv
import kotlinx.android.synthetic.main.favorites_list_item.view.favorite_movie_rating_tv

class FavoriteAdapter(
    private val movies: List<Movie>,
    private var onEndReachedListener: OnEndReachedListener,
    private val onItemClickListener: (Movie, View) -> Unit
)

    : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                ViewCompat.setTransitionName(itemView.favorite_movie_poster_iv, POSTER_TRANSITION_NAME)

                onItemClickListener.invoke(movies[adapterPosition], itemView.favorite_movie_poster_iv)
            }
        }

        fun bindViewHolder(position: Int) {
            val movie = movies[position]
            if (position == movies.size - PopularMovieAdapter.LAST_ITEMS_BEFORE_LOAD_NEW) {
                onEndReachedListener.onEndReached(position)
            }
            if (movie.averageVote != 0F) {
                itemView.favorite_movie_rating_tv!!.text = java.lang.Float.toString(movie.averageVote)
            } else {
                itemView.favorite_movie_rating_tv.text = "Unrated"
            }
            if (movie.posterPath != null) {
                Glide.with(itemView.context)
                    .load(PopularMovieAdapter.IMAGE_BASE_URL + movie.posterPath)
                    .into(itemView.favorite_movie_poster_iv)
            } else {
                itemView.favorite_movie_poster_iv.setImageResource(R.drawable.default_poster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorites_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(position)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
