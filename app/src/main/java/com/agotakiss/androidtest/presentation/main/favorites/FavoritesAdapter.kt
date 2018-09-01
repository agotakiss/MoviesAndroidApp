package com.agotakiss.androidtest.presentation.main.favorites

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.main.MovieAdapter
import com.bumptech.glide.Glide
import io.reactivex.Observable
import kotlinx.android.synthetic.main.favorite_fragment_list_item.view.*

class FavoritesAdapter(
    private val favoriteMovies: List<Movie>,
    private val onClickListener: (Movie) -> Unit,
    private val onFavoriteButtonClickListener: (Int, Int) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onClickListener.invoke(favoriteMovies[adapterPosition])
                Log.d("adapter", " onclick")
            }
            itemView.favorite_imageview_favorite_list_item.setOnClickListener {
                onFavoriteButtonClickListener.invoke(adapterPosition, favoriteMovies[adapterPosition].id)
            }
        }

        fun bindViewHolder(position: Int) {
            val movie = favoriteMovies[position]
            itemView.favoriteTitle.text = movie.title
            Glide.with(itemView.context)
                .load(MovieAdapter.IMAGE_BASE_URL +  movie.posterPath!!)
                .into(itemView.favoritePoster)
            itemView.genres_favorites.text = movieGenresToDisplay(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_fragment_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favoriteMovies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(position)
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