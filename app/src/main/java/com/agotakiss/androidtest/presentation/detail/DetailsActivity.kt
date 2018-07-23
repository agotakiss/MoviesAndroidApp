package com.agotakiss.androidtest.presentation.detail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.method.ScrollingMovementMethod
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.di.Injector
import com.agotakiss.androidtest.base.BaseActivity
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.IMAGE_BASE_URL
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.MOVIE
import com.agotakiss.androidtest.presentation.main.OnEndReachedListener
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_details.*
import java.util.*

class DetailsActivity : BaseActivity() {

    private val movieRepository = Injector.getMovieRepository()

    private var similarMoviesPage = 1
    private var totalPages: Int = 0
    private lateinit var movie: Movie
    internal var similarMovieList: MutableList<Movie> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SimilarMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        movie = intent.getSerializableExtra(MOVIE) as Movie
        initUI()
        loadSimilarMovies()
        initializeSimilarMovieList()
    }

    private fun loadSimilarMovies() {
        movieRepository.getSimilarMovies(movie.id, similarMoviesPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movieList -> onSimilarMoviesLoaded(movieList, Integer.MAX_VALUE) }, { throwable ->
                    logE(throwable)
                })
    }

    private fun initUI() {
        Picasso.get().load(IMAGE_BASE_URL + movie.posterPath!!).into(poster_detailed)
        Picasso.get().load(IMAGE_BASE_URL + movie.backdropPath!!).into(backdrop_detailed)
        backdrop_detailed.alpha = 0.2f

        movie_title_detailed.text = movie.title
        genres_detailed.text = genresToString()
        rating_detailed.text = java.lang.Float.toString(movie.averageVote)
        release_date_detailed.text = movie.releaseDateText!!.substring(0, 4)
        description_detailed.text = movie.overview
        description_detailed.movementMethod = ScrollingMovementMethod()
    }

    private fun onSimilarMoviesLoaded(newMovies: List<Movie>, pages: Int) {
        similarMovieList.addAll(newMovies)
        adapter.notifyItemRangeInserted(similarMovieList.size - newMovies.size, newMovies.size)
        totalPages = pages
    }

    fun initializeSimilarMovieList() {
        recyclerView = findViewById(R.id.similar_recycler_view)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        adapter = SimilarMovieAdapter(similarMovieList, this)
        recyclerView.adapter = adapter
        adapter.setOnEndReachedListener(object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                similarMoviesPage++
                if (similarMoviesPage < totalPages) {
                    loadSimilarMovies()
                }
            }
        })
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
