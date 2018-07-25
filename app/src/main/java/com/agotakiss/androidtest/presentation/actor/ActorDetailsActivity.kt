package com.agotakiss.androidtest.presentation.actor

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Toast
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Actor
import com.agotakiss.androidtest.presentation.BaseActivity
import com.agotakiss.androidtest.presentation.detail.ActorAdapter.Companion.ACTOR_ID
import com.agotakiss.androidtest.presentation.main.MovieAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_actor_details.*
import javax.inject.Inject

class ActorDetailsActivity : BaseActivity(), ActorDetailsView {

    val applicationComponent by lazy { movieApplication.applicationComponent.plus(ActorDetailsModule(this)) }
    private var actorId = 0
    @Inject
    lateinit var presenter: ActorDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor_details)
        applicationComponent.inject(this)
        actorId = intent.getIntExtra(ACTOR_ID, 0)
        presenter.onViewReady(this, actorId)
    }

    override fun initUI(actor: Actor) {
        Picasso.get().load(MovieAdapter.IMAGE_BASE_URL + actor.profilePath).into(actor_detailed_photo_imageview)
        actor_detail_name_textview.text = actor.name
        actor_detail_known_for_department.text = actor.knownForDepartment
        actor_detail_biography.text = actor.biography
        actor_detail_biography.movementMethod = ScrollingMovementMethod()

        if(actor.birthday== null){
            birthday_title.visibility = View.GONE
            actor_detail_birthday_textview.visibility = View.GONE
        } else{
            actor_detail_birthday_textview.text = actor.birthday
        }

    }

    override fun showError(t: Throwable) {
        Toast.makeText(this, "Error loading the actors.", Toast.LENGTH_LONG).show()
        logE(t)    }




}
