package com.agotakiss.androidtest.presentation.home

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.detail.DetailsActivity
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import kotlinx.android.synthetic.main.list_item.view.*


class MovieAdapter(private val movies: List<Movie>, private val context: Context) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var onEndReachedListener: OnEndReachedListener? = null

    fun setOnEndReachedListener(onEndReachedListener: OnEndReachedListener) {
        this.onEndReachedListener = onEndReachedListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.more_info_button.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra(MOVIE, movies[adapterPosition])
                    context.startActivity(intent)
                }
            })
        }

        fun bindViewHolder(position: Int) {
            val movie = movies[position]
            if (position == movies.size - 1) {
                onEndReachedListener!!.onEndReached(position)
            }
            itemView.movie_title.text = movie.title
            itemView.movie_rating.text = java.lang.Float.toString(movie.averageVote)
            itemView.movie_genres.text = movieGenresToDisplay(movie)
            itemView.movie_release_date.text = movie.releaseDateText!!.substring(0, 4)
            itemView.movie_description.text = movie.overview
            Picasso.get().load(IMAGE_BASE_URL + movie.posterPath!!).into(itemView.poster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(position)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun movieGenresToDisplay(movie: Movie): String {
        return if (movie.genres != null && !movie.genres.isEmpty()) {
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
