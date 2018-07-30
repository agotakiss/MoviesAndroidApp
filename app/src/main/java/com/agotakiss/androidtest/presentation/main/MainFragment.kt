package com.agotakiss.androidtest.presentation.main


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.base.MovieApplication
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.detail.DetailsActivity
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*
import javax.inject.Inject

class MainFragment : Fragment(), MainView {


    companion object {
        const val POSTER_TRANSITION_NAME = "posterTransition"
    }

    val movieApplication: MovieApplication get() = MovieApplication.get()
    val applicationComponent by lazy { movieApplication.applicationComponent.plus(MainModule(this)) }

    @Inject
    lateinit var presenter: MainPresenter

    internal var movieList: MutableList<Movie> = ArrayList()
    private lateinit var adapter: MovieAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        applicationComponent.inject(this)
        initializeList()
        presenter.onViewReady(this)
    }


    private fun initializeList() {
        val layoutManager = LinearLayoutManager(context)
        main_recycler_view.layoutManager = layoutManager
        adapter = MovieAdapter(movieList, object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                presenter.onScrollEndReached()
            }
        }) { movie, view ->
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(MovieAdapter.MOVIE, movie)
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity::class.java, view,
//                POSTER_TRANSITION_NAME)
//            options.toBundle()
            startActivity(intent)
        }
        main_recycler_view.adapter = adapter
    }

    override fun showMovies(newMovies: List<Movie>) {
        movieList.addAll(newMovies)
        adapter.notifyItemRangeInserted(movieList.size - newMovies.size, newMovies.size)
    }


}
