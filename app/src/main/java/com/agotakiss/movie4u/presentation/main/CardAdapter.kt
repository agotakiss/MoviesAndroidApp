package com.agotakiss.movie4u.presentation.main

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.movie4u.R
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.presentation.POSTER_TRANSITION_NAME
import com.agotakiss.movie4u.presentation.main.PopularMovieAdapter.Companion.IMAGE_BASE_URL
import com.agotakiss.movie4u.presentation.main.PopularMovieAdapter.Companion.LAST_ITEMS_BEFORE_LOAD_NEW
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_movie_list_item.view.*

class CardAdapter(
    private val movies: List<Movie>,
    private var onEndReachedListener: OnEndReachedListener,
    private val onItemClickListener: (Movie, View) -> Unit
)

    : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                ViewCompat.setTransitionName(itemView.card_movie_poster_iv, POSTER_TRANSITION_NAME)

                onItemClickListener.invoke(movies[adapterPosition], itemView.card_movie_poster_iv)
            }
        }

        fun bindViewHolder(position: Int) {
            val movie = movies[position]
            if (position == movies.size - LAST_ITEMS_BEFORE_LOAD_NEW) {
                onEndReachedListener.onEndReached(position)
            }
            if (movie.averageVote != 0F) {
                itemView.card_movie_rating_tv!!.text = java.lang.Float.toString(movie.averageVote)
            } else {
                itemView.card_movie_rating_tv.text = "Unrated"
            }
            if (movie.posterPath != null) {
                Glide.with(itemView.context)
                    .load(IMAGE_BASE_URL + movie.posterPath)
                    .into(itemView.card_movie_poster_iv)
            } else {
                itemView.card_movie_poster_iv.setImageResource(R.drawable.default_poster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_movie_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(position)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
