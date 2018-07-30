package com.agotakiss.androidtest.presentation.detail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader.TileMode
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agotakiss.androidtest.R
import com.agotakiss.androidtest.domain.models.Cast
import com.agotakiss.androidtest.presentation.actor.ActorDetailsActivity
import com.agotakiss.androidtest.presentation.main.MovieAdapter
import com.agotakiss.androidtest.presentation.main.OnEndReachedListener
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import kotlinx.android.synthetic.main.actors_list_item.view.*


class ActorAdapter(private val actors: List<Cast>,
                   private var onEndReachedListener: OnEndReachedListener,
                   private val onItemClickListener: (Int, View) -> Unit)
    : RecyclerView.Adapter<ActorAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                   onItemClickListener.invoke(actors[adapterPosition].id, itemView.actor_photo_imageview )
                }
            })
        }

        fun bindViewHolder(position: Int) {
            val actor = actors[position]
            if (position == actors.size - 5) {
                onEndReachedListener.onEndReached(position)
            }
            itemView.actor_name_textview.text = actor.name
            itemView.character_name_textview.text = actor.character
//            Picasso.get().load(MovieAdapter.IMAGE_BASE_URL + actor.profilePath)
//                .into(itemView.actor_photo_imageview)
            if (actor.profilePath == null){
                itemView.actor_photo_imageview.setImageResource(R.drawable.person_picture)
            }else{
                Picasso.get()
                    .load(MovieAdapter.IMAGE_BASE_URL + actor.profilePath)
                    .transform(CircleTransform())
                    .into(itemView.actor_photo_imageview)
            }
        }

        inner class CircleTransform : Transformation {
            override fun transform(source: Bitmap?): Bitmap? {
                if (source == null || source.isRecycled) {
                    return null
                }

                val width = source.width
                val height = source.height

                val canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val shader = BitmapShader(source, TileMode.CLAMP, TileMode.CLAMP)
                val paint = Paint()
                paint.isAntiAlias = true
                paint.shader = shader

                val canvas = Canvas(canvasBitmap)
                val radius = if (width > height) height.toFloat() / 2f else width.toFloat() / 2f
                canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

                if (canvasBitmap != source) {
                    source.recycle()
                }
                return canvasBitmap
            }

            override fun key(): String {
                return "circle"
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

