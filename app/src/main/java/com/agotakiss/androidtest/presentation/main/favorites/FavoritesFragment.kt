package com.agotakiss.androidtest.presentation.main.favorites


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.base.MovieApplication
import com.agotakiss.androidtest.data.models.MovieDatabaseModel
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.FAVORITE_MOVIE
import com.agotakiss.androidtest.presentation.detail.DetailsActivity
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.MOVIE
import kotlinx.android.synthetic.main.fragment_favorites.*
import javax.inject.Inject

class FavoritesFragment : Fragment(), FavoritesView {

    val movieApplication: MovieApplication get() = MovieApplication.get()
    val applicationComponent by lazy { movieApplication.applicationComponent.plus(FavoritesModule(this)) }

    @Inject
    lateinit var presenter: FavoritesPresenter

    internal var favoriteMoviesList = mutableListOf<Movie>()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        applicationComponent.inject(this)
        initializeList()
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewReady(this)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) {
            presenter.onViewReady(this)
        }
    }

    private fun initializeList() {
        val layoutManager = LinearLayoutManager(context)
        favorites_recycler_view.layoutManager = layoutManager
        adapter = FavoritesAdapter(favoriteMoviesList, { favoriteMovie ->

            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(MOVIE, favoriteMovie)
            startActivity(intent)
            Log.d("favorites fragment", " onclick")
        })
        favorites_recycler_view.adapter = adapter
    }

    override fun showFavoriteMovies(favoriteMovies: List<Movie>) {
        favoriteMoviesList.clear()
        favoriteMoviesList.addAll(favoriteMovies)
        adapter.notifyDataSetChanged()
    }
}
