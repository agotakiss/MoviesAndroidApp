package com.agotakiss.androidtest.presentation.detail

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Cast
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
    internal var actorsList: MutableList<Cast> = ArrayList()
    private lateinit var adapter: SimilarMovieAdapter
    private lateinit var actorAdapter: ActorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        applicationComponent.inject(this)
        movie = intent.getSerializableExtra(MOVIE) as Movie
        initUI()
        initializeSimilarMovieList()
        initializeActorsList()
        presenter.onViewReady(this, movie)
        description_detailed.setOnClickListener { expandCollapsedByMaxLines(description_detailed) }
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        setTitle(movie.title)

        Picasso.get().load(IMAGE_BASE_URL + movie.posterPath).into(poster_detailed)
        Picasso.get().load(IMAGE_BASE_URL + movie.backdropPath).into(backdrop_detailed)
        Picasso.get().load(IMAGE_BASE_URL + movie.backdropPath).into(collapsing_image)
        backdrop_detailed.alpha = 0.2f
        movie_title_detailed.text = movie.title
        genres_detailed.text = genresToString()
        rating_detailed.text = java.lang.Float.toString(movie.averageVote)
        release_date_detailed.text = movie.releaseDateText!!.substring(0, 4)
        description_detailed.text = movie.overview
    }

    fun initializeSimilarMovieList() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        similar_recycler_view.layoutManager = layoutManager
        adapter = SimilarMovieAdapter(similarMovieList, this)
        similar_recycler_view.adapter = adapter
        adapter.setOnEndReachedListener(object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                presenter.onSimilarMovieScrollEndReached()
            }
        })
    }

    override fun showSimilarMovies(newMovies: List<Movie>) {
        similarMovieList.addAll(newMovies)
        adapter.notifyItemRangeInserted(similarMovieList.size - newMovies.size, newMovies.size)
    }

    fun initializeActorsList() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        actors_recycler_view.layoutManager = layoutManager
        actorAdapter = ActorAdapter(actorsList, this)
        actors_recycler_view.adapter = actorAdapter
        actorAdapter.setOnEndReachedListener(object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                presenter.onSimilarMovieScrollEndReached()
            }
        })
    }

    override fun showActors(newActors: List<Cast>) {
        actorsList.addAll(newActors)
        actorAdapter.notifyItemRangeInserted(actorsList.size - newActors.size, newActors.size)
    }

    override fun showError(t: Throwable) {
        Toast.makeText(this, "Error loading the movies.", Toast.LENGTH_LONG).show()
        logE(t)
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

    @SuppressLint("Range")
    fun expandCollapsedByMaxLines(text: TextView) {
        val height = text.measuredHeight
        text.height = height
        text.maxLines = Integer.MAX_VALUE //expand fully
        text.measure(View.MeasureSpec.makeMeasureSpec(text.measuredWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.UNSPECIFIED))
        val newHeight = text.measuredHeight
        val animation = ObjectAnimator.ofInt(text, "height", height, newHeight)
        animation.setDuration(250).start()
    }
}
