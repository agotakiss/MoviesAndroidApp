package com.agotakiss.movie4u.presentation.main.favorites

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.movie4u.R
import com.agotakiss.movie4u.base.MovieApplication
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.presentation.MOVIE
import com.agotakiss.movie4u.presentation.POSTER_TRANSITION_NAME
import com.agotakiss.movie4u.presentation.detail.DetailsActivity
import com.agotakiss.movie4u.presentation.main.MainPageFragment
import com.agotakiss.movie4u.presentation.main.OnEndReachedListener
import kotlinx.android.synthetic.main.fragment_favorites.*
import javax.inject.Inject

class FavoritesFragment : MainPageFragment(), FavoritesView {

    private val movieApplication: MovieApplication get() = MovieApplication.get()
    private val applicationComponent by lazy { movieApplication.applicationComponent.plus(FavoritesModule(this)) }
    private var noFavoritesView: View? = null

    @Inject
    lateinit var presenter: FavoritesPresenter

    private var favoriteMoviesList = mutableListOf<Movie>()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        applicationComponent.inject(this)
        initializeList()
        presenter.onViewReady(this)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) {
            presenter.onViewReady(this)
        }
    }

    override fun onPageShow() {
        presenter.onPageShow()
    }

    private fun initializeList() {
        val layoutManager = GridLayoutManager(context, 3)
        favorites_rv.layoutManager = layoutManager
        adapter = FavoriteAdapter(favoriteMoviesList, object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
            }
        }) { movie, view ->
            //            ViewCompat.setTransitionName(detail_poster_iv, "random")
//            sharedImageView = view
//            hasStartedAnotherScreen = true
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(MOVIE, movie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as Activity, view,
                POSTER_TRANSITION_NAME)
            startActivityForResult(intent, 123, options.toBundle())
        }
        favorites_rv.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult()
    }

    override fun showFavoriteMovies(favoriteMovies: List<Movie>) {
        favoriteMoviesList.clear()
        favoriteMoviesList.addAll(favoriteMovies)
        adapter.notifyDataSetChanged()
        noFavoritesView?.visibility = View.GONE
    }

    override fun showNoFavoritesView() {
        if (noFavoritesView == null) {
            noFavoritesView = no_favorites_stub.inflate()
        } else {
            noFavoritesView?.visibility = View.VISIBLE
        }
    }

    override fun updateFavoriteMovies(position: Int) {
        favoriteMoviesList.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
}
