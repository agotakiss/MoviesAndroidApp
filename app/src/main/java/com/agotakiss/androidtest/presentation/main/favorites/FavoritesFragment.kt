package com.agotakiss.androidtest.presentation.main.favorites


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.base.MovieApplication
import com.agotakiss.androidtest.domain.models.Movie
import kotlinx.android.synthetic.main.fragment_favorites.*
import java.util.ArrayList
import javax.inject.Inject

class FavoritesFragment : Fragment(), FavoritesView {

    val movieApplication: MovieApplication get() = MovieApplication.get()
    val applicationComponent by lazy { movieApplication.applicationComponent.plus(FavoritesModule(this)) }

    @Inject
    lateinit var presenter: FavoritesPresenter

    internal var favoriteMoviesList: MutableList<Movie> = ArrayList()
    private lateinit var adapter: FavoritesAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        applicationComponent.inject(this)
        initializeList()
        presenter.onViewReady(this)
    }

    private fun initializeList() {
        val layoutManager = LinearLayoutManager(context)
        favorites_recycler_view.layoutManager = layoutManager
        adapter = FavoritesAdapter(favoriteMoviesList)
        favorites_recycler_view.adapter = adapter

    }

    override fun showFavoriteMovies(favoriteMovies: List<Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
