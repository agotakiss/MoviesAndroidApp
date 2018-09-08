package com.agotakiss.movie4u.presentation.detail

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
import com.agotakiss.movie4u.R
import com.agotakiss.movie4u.domain.models.Cast
import com.agotakiss.movie4u.domain.models.Movie
import com.agotakiss.movie4u.presentation.BACKDROP_IMAGE_BASE_URL
import com.agotakiss.movie4u.presentation.BaseActivity
import com.agotakiss.movie4u.presentation.DETAILS_ACTIVITY_RESULT_CODE
import com.agotakiss.movie4u.presentation.IMAGE_BASE_URL
import com.agotakiss.movie4u.presentation.MOVIE
import com.agotakiss.movie4u.presentation.POSTER_TRANSITION_NAME
import com.agotakiss.movie4u.presentation.actor.ActorDetailsActivity
import com.agotakiss.movie4u.presentation.main.OnEndReachedListener
import com.agotakiss.movie4u.presentation.main.popular.CardAdapter
import com.bumptech.glide.Glide
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_details.detail_actors_rv
import kotlinx.android.synthetic.main.activity_details.detail_app_bar_layout_favorite_btn
import kotlinx.android.synthetic.main.activity_details.detail_backdrop_iv
import kotlinx.android.synthetic.main.activity_details.detail_calendar_iv
import kotlinx.android.synthetic.main.activity_details.detail_collapsing_iv
import kotlinx.android.synthetic.main.activity_details.detail_collapsing_toolbar_layout
import kotlinx.android.synthetic.main.activity_details.detail_description_tv
import kotlinx.android.synthetic.main.activity_details.detail_genres_tv
import kotlinx.android.synthetic.main.activity_details.detail_movie_title_tv
import kotlinx.android.synthetic.main.activity_details.detail_poster_iv
import kotlinx.android.synthetic.main.activity_details.detail_rating_tv
import kotlinx.android.synthetic.main.activity_details.detail_release_date_tv
import kotlinx.android.synthetic.main.activity_details.detail_similar_movies_rv
import kotlinx.android.synthetic.main.activity_details.toolbar
import java.util.ArrayList
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
    private lateinit var adapter: CardAdapter
    private lateinit var actorAdapter: ActorAdapter
    private var hasStartedAnotherScreen = false
    private var hasChangedMovieFavoriteState = false

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
        detail_description_tv.setOnClickListener { expandCollapsedByMaxLines(detail_description_tv) }
        detail_app_bar_layout_favorite_btn.setOnClickListener { presenter.onFavoriteButtonClicked(movie) }
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        title = movie.title
        detail_collapsing_toolbar_layout.expandedTitleMarginEnd = 64
        detail_movie_title_tv.text = movie.title
        detail_genres_tv.text = genresToString()

        if (movie.averageVote == 0F) {
            detail_rating_tv.text = getString(R.string.unrated)
        } else {
            detail_rating_tv.text = java.lang.Float.toString(movie.averageVote)
        }

        if (movie.posterPath != null) {
            Glide.with(this).load(IMAGE_BASE_URL + movie.posterPath).into(detail_poster_iv)
        } else {
            detail_poster_iv.setImageResource(R.drawable.default_poster)
        }

        if (movie.backdropPath != null) {
            Glide.with(this).load(BACKDROP_IMAGE_BASE_URL + movie.backdropPath).into(detail_backdrop_iv)
            Glide.with(this).load(BACKDROP_IMAGE_BASE_URL + movie.backdropPath).into(detail_collapsing_iv)
            detail_backdrop_iv.alpha = 0.2f
        } else {
            detail_collapsing_iv.setImageResource(R.drawable.default_poster)
        }

        setFavoriteButton(movie.isFavorite)

        if (movie.releaseDateText != "") {
            detail_release_date_tv.text = movie.releaseDateText!!.substring(0, 4)
        } else {
            detail_calendar_iv.visibility = View.INVISIBLE
        }
        if (movie.overview == "") {
            detail_description_tv.text = "No overview found for this movie."
        } else {
            detail_description_tv.text = movie.overview
        }
    }

    fun initializeSimilarMovieList() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detail_similar_movies_rv.layoutManager = layoutManager
        adapter = CardAdapter(
            similarMovieList,
            object : OnEndReachedListener {
                override fun onEndReached(position: Int) {
                    presenter.onSimilarMovieScrollEndReached()
                }
            }) { movie, view ->
            ViewCompat.setTransitionName(detail_poster_iv, "random")
            sharedImageView = view
            hasStartedAnotherScreen = true
            val similarMovie = movie
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(MOVIE, similarMovie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, view,
                POSTER_TRANSITION_NAME
            )
            startActivity(intent, options.toBundle())
        }
        detail_similar_movies_rv.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        if (hasStartedAnotherScreen) {
            hasStartedAnotherScreen = false
            ViewCompat.setTransitionName(sharedImageView, "unused")
            ViewCompat.setTransitionName(detail_poster_iv, POSTER_TRANSITION_NAME)
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
        if (hasChangedMovieFavoriteState) {
            val intent = Intent()
            intent.putExtra(MOVIE, movie)
            setResult(DETAILS_ACTIVITY_RESULT_CODE, intent)
        }
    }

    override fun setFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            detail_app_bar_layout_favorite_btn.setImageResource(R.drawable.favorite_full_white)
        } else {
            detail_app_bar_layout_favorite_btn.setImageResource(R.drawable.favorite_white)
        }
    }

    override fun hasChangedFavoriteState() {
        hasChangedMovieFavoriteState = true
    }

    override fun showSimilarMovies(newMovies: List<Movie>) {
        similarMovieList.addAll(newMovies)
        adapter.notifyItemRangeInserted(similarMovieList.size - newMovies.size, newMovies.size)
    }

    fun initializeActorsList() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detail_actors_rv.layoutManager = layoutManager
        actorAdapter = ActorAdapter(actorsList, object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
                presenter.onSimilarMovieScrollEndReached()
            }
        }) { actorId, view ->
            ViewCompat.setTransitionName(detail_poster_iv, "random")

            val intent = Intent(this, ActorDetailsActivity::class.java)
            intent.putExtra(ActorAdapter.ACTOR_ID, actorId)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, view,
                ACTOR_TRANSITION_NAME
            )
            startActivity(intent, options.toBundle())
        }
        detail_actors_rv.adapter = actorAdapter
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
        text.maxLines = Integer.MAX_VALUE // expand fully
        text.measure(
            View.MeasureSpec.makeMeasureSpec(text.measuredWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.UNSPECIFIED)
        )
        val newHeight = text.measuredHeight
        val animation = ObjectAnimator.ofInt(text, "height", height, newHeight)
        animation.setDuration(250).start()
    }
}
