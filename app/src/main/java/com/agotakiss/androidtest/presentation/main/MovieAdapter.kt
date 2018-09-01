package com.agotakiss.androidtest.presentation.main

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.POSTER_TRANSITION_NAME
import com.bumptech.glide.Glide
import io.reactivex.Observable
import kotlinx.android.synthetic.main.main_list_item.view.*


class MovieAdapter(
    private val movies: List<Movie>,
    private val onEndReachedListener: OnEndReachedListener,
    private val onItemClickListener: (Movie, View) -> Unit,
    private val onFavoriteButtonClickListener: (Int) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                ViewCompat.setTransitionName(itemView.poster, POSTER_TRANSITION_NAME)
                onItemClickListener.invoke(movies[adapterPosition], itemView.poster)
            }
            itemView.favorite_imageview.setOnClickListener {
                onFavoriteButtonClickListener.invoke(adapterPosition)
            }

        }

        fun bindViewHolder(position: Int) {
            val movie = movies[position]
            if (position == movies.size - 1) {
                onEndReachedListener.onEndReached(position)
            }
            itemView.movie_title.text = movie.title
            itemView.movie_rating.text = java.lang.Float.toString(movie.averageVote)
            itemView.movie_genres.text = movieGenresToDisplay(movie)
            if (movie.releaseDateText != "") {
                itemView.movie_release_date.text = movie.releaseDateText?.substring(0, 4)
            }
            itemView.movie_description.text = movie.overview
            if (movie.posterPath != null) {
                Glide.with(itemView.context)
                    .load(IMAGE_BASE_URL + movie.posterPath!!)
                    .into(itemView.poster)
            }

            if (movie.isFavorite) {
                itemView.favorite_imageview.setImageResource(R.drawable.favorite_full_pic)
            } else {
                itemView.favorite_imageview.setImageResource(R.drawable.favorite_pic)
            }

//            ViewCompat.setTransitionName(itemView.poster, POSTER_TRANSITION_NAME)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)
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

    companion object {
        val IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342/"
        val LAST_ITEMS_BEFORE_LOAD_NEW = 5
        val MOVIE = "movie"
    }
}
