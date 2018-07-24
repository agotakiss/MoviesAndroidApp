package com.agotakiss.androidtest.presentation.detail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.BaseActivity
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.IMAGE_BASE_URL
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.MOVIE
import com.agotakiss.androidtest.presentation.main.OnEndReachedListener
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_details.*
import java.util.*
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsView {

    val applicationComponent by lazy { movieApplication.applicationComponent.plus(DetailsModule(this)) }

    @Inject
    lateinit var presenter: DetailsPresenter
    private lateinit var movie: Movie
    internal var similarMovieList: MutableList<Movie> = ArrayList()
    private lateinit var adapter: SimilarMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        applicationComponent.inject(this)
        movie = intent.getSerializableExtra(MOVIE) as Movie
        initUI()
        initializeSimilarMovieList()
        presenter.onViewReady(this, movie)
    }

    private fun initUI() {
        Picasso.get().load(IMAGE_BASE_URL + movie.posterPath).into(poster_detailed)
        Picasso.get().load(IMAGE_BASE_URL + movie.backdropPath).into(backdrop_detailed)
        backdrop_detailed.alpha = 0.2f
        movie_title_detailed.text = movie.title
        genres_detailed.text = genresToString()
        rating_detailed.text = java.lang.Float.toString(movie.averageVote)
        release_date_detailed.text = movie.releaseDateText!!.substring(0, 4)
        description_detailed.text = movie.overview
        description_detailed.movementMethod = ScrollingMovementMethod()
    }

    override fun showSimilarMovies(newMovies: List<Movie>) {
        similarMovieList.addAll(newMovies)
        adapter.notifyItemRangeInserted(similarMovieList.size - newMovies.size, newMovies.size)
    }

    fun initializeSimilarMovieList() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        similar_recycler_view.layoutManager = layoutManager
        adapter = SimilarMovieAdapter(similarMovieList, this)
        similar_recycler_view.adapter = adapter
        adapter.setOnEndReachedListener(object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                presenter.onScrollEndReached()
            }
        })
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, "Error loading the movies.", Toast.LENGTH_LONG).show()
        logE(throwable)
    }

    fun genresToString(): String {
        return if (movie.genres != null && !movie.genres!!.isEmpty()) {
            Observable.fromIterable(movie.genres!!)
                .map<String> { it.name }
                .reduce { s, s2 -> "$s, $s2" }.blockingGet()
        } else {
            ""
        }
    }
}
