package com.agotakiss.androidtest.presentation.main.favorites

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.main.MovieAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favorite_fragment_list_item.view.*

class FavoritesAdapter(
    private val favoriteMovies: List<Movie>
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewHolder(position: Int) {
            val movie = favoriteMovies[position]
            itemView.favoriteTitle.text = movie.title
            Picasso.get().load(MovieAdapter.IMAGE_BASE_URL + movie.posterPath!!).into(itemView.favoritePoster)
            itemView.addedOnDate.text = "2019.07.27"
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

}