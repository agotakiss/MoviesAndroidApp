package com.agotakiss.movie4u.presentation.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.movie4u.R
import com.agotakiss.movie4u.domain.models.Cast
import com.agotakiss.movie4u.presentation.IMAGE_BASE_URL
import com.agotakiss.movie4u.presentation.main.OnEndReachedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.actors_list_item.view.actor_name_tv
import kotlinx.android.synthetic.main.actors_list_item.view.actor_photo_iv
import kotlinx.android.synthetic.main.actors_list_item.view.character_name_tv

class ActorAdapter(
    private val actors: List<Cast>,
    private var onEndReachedListener: OnEndReachedListener,
    private val onItemClickListener: (Int, View) -> Unit
) : RecyclerView.Adapter<ActorAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onItemClickListener.invoke(actors[adapterPosition].id, itemView.actor_photo_iv)
                }
            })
        }

        fun bindViewHolder(position: Int) {
            val actor = actors[position]
            if (position == actors.size - 5) {
                onEndReachedListener.onEndReached(position)
            }
            itemView.actor_name_tv.text = actor.name
            itemView.character_name_tv.text = actor.character
            if (actor.profilePath == null) {
                itemView.actor_photo_iv.setImageResource(R.drawable.person_picture)
            } else {
                Glide.with(itemView.context)
                    .load(IMAGE_BASE_URL + actor.profilePath)
                    .apply(RequestOptions.circleCropTransform())
                    .into(itemView.actor_photo_iv)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.actors_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(position)
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    companion object {
        val ACTOR_ID = "actor_id"
    }
}
