package com.agotakiss.androidtest.presentation.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.IMAGE_BASE_URL
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.LAST_ITEMS_BEFORE_LOAD_NEW
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.MOVIE
import com.agotakiss.androidtest.presentation.main.OnEndReachedListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.similar_movie_list_item.view.*

class SimilarMovieAdapter(private val similarMovies: List<Movie>, private val context: Context) : RecyclerView.Adapter<SimilarMovieAdapter.ViewHolder>() {
    private var onEndReachedListener: OnEndReachedListener? = null

    fun setOnEndReachedListener(onEndReachedListener: OnEndReachedListener) {
        this.onEndReachedListener = onEndReachedListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val similarMovie = similarMovies[adapterPosition]
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra(MOVIE, similarMovie)
                    (context as Activity).finish();
                    context.startActivity(intent);
                }
            })
        }

        fun bindViewHolder(position: Int) {
            val similarMovie = similarMovies[position]
            if (position == similarMovies.size - LAST_ITEMS_BEFORE_LOAD_NEW) {
                onEndReachedListener!!.onEndReached(position)
            }
            itemView.similar_movie_rating!!.text = java.lang.Float.toString(similarMovie.averageVote)
            Picasso.get().load(IMAGE_BASE_URL + similarMovie.posterPath).into(itemView.similar_movie_poster)
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
