package com.agotakiss.androidtest.presentation.detail

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Cast
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.BaseActivity
import com.agotakiss.androidtest.presentation.actor.ActorDetailsActivity
import com.agotakiss.androidtest.presentation.main.MainActivity
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.IMAGE_BASE_URL
import com.agotakiss.androidtest.presentation.main.MovieAdapter.Companion.MOVIE
import com.agotakiss.androidtest.presentation.main.OnEndReachedListener
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_details.*
import java.util.*
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsView {

    companion object {
        const val ACTOR_TRANSITION_NAME = "actorTransition"
    }

    val applicationComponent by lazy { movieApplication.applicationComponent.plus(DetailsModule(this)) }

    @Inject
    lateinit var presenter: DetailsPresenter

    private lateinit var movie: Movie
    internal var similarMovieList: MutableList<Movie> = ArrayList()
    internal var actorsList: MutableList<Cast> = ArrayList()
    private lateinit var adapter: SimilarMovieAdapter
    private lateinit var actorAdapter: ActorAdapter
    private var hasStartedAnotherScreen = false

    private var sharedImageView: View? = null

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
        adapter = SimilarMovieAdapter(similarMovieList, object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                presenter.onSimilarMovieScrollEndReached()
            }
        }) { movie, view ->
            ViewCompat.setTransitionName(poster_detailed, "random")
            sharedImageView = view
            hasStartedAnotherScreen = true
            val similarMovie = movie
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(MOVIE, similarMovie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view,
                MainActivity.POSTER_TRANSITION_NAME)
            startActivity(intent, options.toBundle())
        }
        similar_recycler_view.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        if (hasStartedAnotherScreen) {
            hasStartedAnotherScreen = false
            ViewCompat.setTransitionName(sharedImageView, "unused")
            ViewCompat.setTransitionName(poster_detailed, MainActivity.POSTER_TRANSITION_NAME)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    override fun showSimilarMovies(newMovies: List<Movie>) {
        similarMovieList.addAll(newMovies)
        adapter.notifyItemRangeInserted(similarMovieList.size - newMovies.size, newMovies.size)
    }

    fun initializeActorsList() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        actors_recycler_view.layoutManager = layoutManager
        actorAdapter = ActorAdapter(actorsList, object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                presenter.onSimilarMovieScrollEndReached()
            }
        }) { actorId, view ->
            ViewCompat.setTransitionName(poster_detailed, "random")

            val intent = Intent(this, ActorDetailsActivity::class.java)
            intent.putExtra(ActorAdapter.ACTOR_ID, actorId)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view,
                ACTOR_TRANSITION_NAME)
            startActivity(intent, options.toBundle())
        }
        actors_recycler_view.adapter = actorAdapter
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
