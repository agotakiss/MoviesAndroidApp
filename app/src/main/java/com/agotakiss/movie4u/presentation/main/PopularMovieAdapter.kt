package com.agotakiss.movie4u.presentation.main

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.movie4u.R
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.presentation.IMAGE_BASE_URL
import com.agotakiss.movie4u.presentation.POSTER_TRANSITION_NAME
import com.bumptech.glide.Glide
import io.reactivex.Observable
import kotlinx.android.synthetic.main.popular_movie_list_item.view.popular_calendar_iv
import kotlinx.android.synthetic.main.popular_movie_list_item.view.popular_favorite_iv
import kotlinx.android.synthetic.main.popular_movie_list_item.view.popular_movie_description_tv
import kotlinx.android.synthetic.main.popular_movie_list_item.view.popular_movie_genres_tv
import kotlinx.android.synthetic.main.popular_movie_list_item.view.popular_movie_rating_tv
import kotlinx.android.synthetic.main.popular_movie_list_item.view.popular_movie_release_date_tv
import kotlinx.android.synthetic.main.popular_movie_list_item.view.popular_movie_title_tv
import kotlinx.android.synthetic.main.popular_movie_list_item.view.popular_poster_iv

class PopularMovieAdapter(
    private val movies: List<Movie>,
    private val onEndReachedListener: OnEndReachedListener,
    private val onItemClickListener: (Movie, View) -> Unit,
    private val onFavoriteButtonClickListener: (Int) -> Unit
) : RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                ViewCompat.setTransitionName(itemView.popular_poster_iv, POSTER_TRANSITION_NAME)
                onItemClickListener.invoke(movies[adapterPosition], itemView.popular_poster_iv)
            }
            itemView.popular_favorite_iv.setOnClickListener {
                onFavoriteButtonClickListener.invoke(adapterPosition)
            }
        }

        fun bindViewHolder(position: Int) {
            val movie = movies[position]
            if (position == movies.size - 1) {
                onEndReachedListener.onEndReached(position)
            }
            itemView.popular_movie_title_tv.text = movie.title
            if (movie.averageVote != 0F) {
                itemView.popular_movie_rating_tv.text = java.lang.Float.toString(movie.averageVote)
            } else {
                itemView.popular_movie_rating_tv.text = "Unrated"
            }
            itemView.popular_movie_genres_tv.text = movieGenresToDisplay(movie)
            if (movie.releaseDateText != "") {
                itemView.popular_movie_release_date_tv.text = movie.releaseDateText?.substring(0, 4)
                itemView.popular_calendar_iv.visibility = View.VISIBLE
            } else {
                itemView.popular_movie_release_date_tv.text = ""
                itemView.popular_calendar_iv.visibility = View.INVISIBLE
            }
            itemView.popular_movie_description_tv.text = movie.overview
            if (movie.posterPath != null) {
                Glide.with(itemView.context)
                    .load(IMAGE_BASE_URL + movie.posterPath!!)
                    .into(itemView.popular_poster_iv)
            } else {
                itemView.popular_poster_iv.setImageResource(R.drawable.default_poster)
            }

            if (movie.isFavorite) {
                itemView.popular_favorite_iv.setImageResource(R.drawable.favorite_full_pic)
            } else {
                itemView.popular_favorite_iv.setImageResource(R.drawable.favorite_pic)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_movie_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(position)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun movieGenresToDisplay(movie: Movie): String {
        return if (movie.genres != null && !movie.genres!!.isEmpty()) {
            Observable.fromIterable(movie.genres)
                .map<String> { it.name }
                .reduce { s, s2 -> "$s, $s2" }.blockingGet()
        } else {
            ""
        }
    }
}
