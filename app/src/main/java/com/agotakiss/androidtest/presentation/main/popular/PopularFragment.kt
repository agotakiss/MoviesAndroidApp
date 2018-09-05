package com.agotakiss.androidtest.presentation.main.popular


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.base.MovieApplication
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.detail.DetailsActivity
import com.agotakiss.androidtest.presentation.main.PopularMovieAdapter
import com.agotakiss.androidtest.presentation.main.OnEndReachedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_popular.*
import org.greenrobot.eventbus.EventBus
import java.util.*
import javax.inject.Inject

class PopularFragment : Fragment(), PopularView {

    companion object {
        const val POSTER_TRANSITION_NAME = "posterTransition"
    }

    val movieApplication: MovieApplication get() = MovieApplication.get()
    val applicationComponent by lazy { movieApplication.applicationComponent.plus(PopularModule(this)) }

    @Inject
    lateinit var presenter: PopularPresenter

    internal var movieList: MutableList<Movie> = ArrayList()
    private lateinit var adapter: PopularMovieAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity::class, view,
//                POSTER_TRANSITION_NAME)
//            startActivity(intent, options.toBundle())
            startActivity(intent)
        }, presenter::onFavoriteButtonClicked)

        popular_movies_rv.adapter = adapter
    }

    override fun showMovies(newMovies: List<Movie>) {
        movieList.addAll(newMovies)
        adapter.notifyItemRangeInserted(movieList.size - newMovies.size, newMovies.size)
        loading_av.visibility = View.GONE
    }

    override fun updateListItem(position: Int) {
        adapter.notifyItemChanged(position)
    }

    override fun showError(t: Throwable) {
        Toast.makeText(activity, getString(R.string.error), Toast.LENGTH_LONG).show()
        Log.e("PopularFragment", t.toString())
        presenter.onViewReady(this)
    }
}
