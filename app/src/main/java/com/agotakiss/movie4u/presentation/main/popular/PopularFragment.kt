package com.agotakiss.movie4u.presentation.main.popular

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.agotakiss.movie4u.R
import com.agotakiss.movie4u.base.MovieApplication
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.presentation.detail.DetailsActivity
import com.agotakiss.movie4u.presentation.main.MainActivity
import com.agotakiss.movie4u.presentation.main.MainPageFragment
import com.agotakiss.movie4u.presentation.main.OnEndReachedListener
import com.agotakiss.movie4u.presentation.main.PopularMovieAdapter
import kotlinx.android.synthetic.main.fragment_popular.popular_movies_rv
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

class PopularFragment : MainPageFragment(), PopularView {

    companion object {
        const val POSTER_TRANSITION_NAME = "posterTransition"
    }

    val movieApplication: MovieApplication get() = MovieApplication.get()
    val applicationComponent by lazy { movieApplication.applicationComponent.plus(PopularModule(this)) }

    @Inject
    lateinit var presenter: PopularPresenter

    internal var movieList: MutableList<Movie> = ArrayList()
    private lateinit var adapter: PopularMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        applicationComponent.inject(this)
        initializeList()
        presenter.onViewReady(this)
        EventBus.getDefault().register(presenter)
    }

    private fun initializeList() {
        val layoutManager = LinearLayoutManager(context)
        popular_movies_rv.layoutManager = layoutManager
        adapter = PopularMovieAdapter(movieList, object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                presenter.onScrollEndReached()
            }
        }, { movie, view ->
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(PopularMovieAdapter.MOVIE, movie)
            Timber.d("details of the movie ${movie.title} and it is ${movie.isFavorite}")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity as Activity, view,
                POSTER_TRANSITION_NAME
            )
            startActivity(intent, options.toBundle())
        }, presenter::onFavoriteButtonClicked)

        popular_movies_rv.adapter = adapter
    }

    override fun showMovies(newMovies: List<Movie>) {
        movieList.addAll(newMovies)
        adapter.notifyItemRangeInserted(movieList.size - newMovies.size, newMovies.size)
    }

    override fun hideLoadingView() {
        activity?.let {
            (activity as MainActivity).hideLoadingView()
        }
    }

    override fun updateListItem(position: Int) {
        adapter.notifyItemChanged(position)
    }

    override fun showError(t: Throwable) {
        Toast.makeText(activity, getString(R.string.error), Toast.LENGTH_LONG).show()
        Timber.e(t)
        Handler().postDelayed({
            presenter.onViewReady(this)
        }, 5000)
    }
}
