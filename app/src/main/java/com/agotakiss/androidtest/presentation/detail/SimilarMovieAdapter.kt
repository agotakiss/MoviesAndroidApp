package com.agotakiss.androidtest.presentation.detail

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.POSTER_TRANSITION_NAME
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.IMAGE_BASE_URL
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.LAST_ITEMS_BEFORE_LOAD_NEW
import com.agotakiss.androidtest.presentation.main.OnEndReachedListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.similar_movie_list_item.view.*

class SimilarMovieAdapter(private val similarMovies: List<Movie>,
                          private var onEndReachedListener: OnEndReachedListener,
                          private val onItemClickListener: (Movie, View) -> Unit)

    : RecyclerView.Adapter<SimilarMovieAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    ViewCompat.setTransitionName(itemView.similar_movie_poster, POSTER_TRANSITION_NAME)

                    onItemClickListener.invoke(similarMovies[adapterPosition], itemView.similar_movie_poster)
                }
            })
        }

        fun bindViewHolder(position: Int) {
            val similarMovie = similarMovies[position]
            if (position == similarMovies.size - LAST_ITEMS_BEFORE_LOAD_NEW) {
                onEndReachedListener.onEndReached(position)
            }
            if (similarMovie.averageVote != 0F){
                itemView.similar_movie_rating!!.text = java.lang.Float.toString(similarMovie.averageVote)
            } else{
                itemView.similar_movie_rating.text = "Unrated"
            }
            if (similarMovie.posterPath != null){
                Glide.with(itemView.context)
                    .load(IMAGE_BASE_URL + similarMovie.posterPath)
                    .into(itemView.similar_movie_poster)
            }else{
                itemView.similar_movie_poster.setImageResource(R.drawable.default_poster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.similar_movie_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(position)
    }

    override fun getItemCount(): Int {
        return similarMovies.size
    }
}
