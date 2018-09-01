package com.agotakiss.androidtest.presentation.actor

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Actor
import com.agotakiss.androidtest.domain.models.Movie
import com.agotakiss.androidtest.presentation.BaseActivity
import com.agotakiss.androidtest.presentation.POSTER_TRANSITION_NAME
import com.agotakiss.androidtest.presentation.detail.ActorAdapter.Companion.ACTOR_ID
import com.agotakiss.androidtest.presentation.detail.DetailsActivity
import com.agotakiss.androidtest.presentation.detail.SimilarMovieAdapter
import com.agotakiss.androidtest.presentation.main.MovieAdapter
import com.agotakiss.androidtest.presentation.main.OnEndReachedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_actor_details.*
import java.util.*
import javax.inject.Inject


class ActorDetailsActivity : BaseActivity(), ActorDetailsView {

    val applicationComponent by lazy { movieApplication.applicationComponent.plus(ActorDetailsModule(this)) }
    @Inject
    lateinit var presenter: ActorDetailsPresenter

    private lateinit var actorsMoviesAdapter: SimilarMovieAdapter
    internal var actorsMoviesList: MutableList<Movie> = ArrayList()
    private var actorId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor_details)
        applicationComponent.inject(this)
        actorId = intent.getIntExtra(ACTOR_ID, 0)
        initActorsMovieList()
        presenter.onViewReady(this, actorId)
        actor_detail_biography.setOnClickListener { expandCollapsedByMaxLines(actor_detail_biography) }
    }

    override fun initUI(actor: Actor) {
        if (actor.profilePath == null) {
            actor_detailed_photo_imageview.setImageResource(R.drawable.person_picture)
        } else {
            Glide.with(this)
                .load(MovieAdapter.IMAGE_BASE_URL + actor.profilePath)
                .apply(RequestOptions.circleCropTransform())
                .into(actor_detailed_photo_imageview)
        }
        actor_detail_name_textview.text = actor.name
        actor_detail_known_for_department.text = actor.knownForDepartment
        actor_detail_biography.text = actor.biography
        if (actor.birthday == null) {
            birthday_title.visibility = View.GONE
            actor_detail_birthday_textview.visibility = View.GONE
        } else {
            actor_detail_birthday_textview.text = actor.birthday
        }
    }

    fun initActorsMovieList() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        actors_movies_recycler_view.layoutManager = layoutManager
        actorsMoviesAdapter = SimilarMovieAdapter(actorsMoviesList, object : OnEndReachedListener {
            override fun onEndReached(position: Int) {
            }
        }) { movie, view ->
            val similarMovie = movie
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(MovieAdapter.MOVIE, similarMovie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view,
                POSTER_TRANSITION_NAME)
            startActivity(intent, options.toBundle())
        }
        actors_movies_recycler_view.adapter = actorsMoviesAdapter
    }

    override fun showActorsMovies(actorsNewMoviesList: List<Movie>) {
        actorsMoviesList.addAll(actorsNewMoviesList)
        actorsMoviesAdapter.notifyItemRangeInserted(actorsMoviesList.size - actorsNewMoviesList.size,
            actorsNewMoviesList.size)
    }

    override fun showError(t: Throwable) {
        Toast.makeText(this, "Error loading the actors.", Toast.LENGTH_LONG).show()
        logE(t)
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
