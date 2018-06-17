package com.example.tarekbaz.watch_up.Adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.tarekbaz.watch_up.Config
import com.example.tarekbaz.watch_up.FilmDetailActivity
import com.example.tarekbaz.watch_up.Models.Movie
import com.example.tarekbaz.watch_up.Offline.ImageManager
import com.example.tarekbaz.watch_up.R


class HomeMovieRecyclerViewAdapter(private val mContext: Context, val films: List<Movie>, val offline: Boolean = false) : RecyclerView.Adapter<HomeMovieRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_home_film, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (!offline) {
            if (null != films.get(position).poster_path)
                Glide.with(mContext)
                        .load(Config.IMG_BASE_URL + films.get(position).poster_path)
                        .into(holder.image)
            else
                holder.image.setImageResource(R.drawable.no_img1)
        } else {
            val image = ImageManager.getImageFromPath(films.get(position).id)
            if (image != null) {
                holder.image.setImageBitmap(image)
            }
            if (mContext is FilmDetailActivity) {
                holder.image.isEnabled = false
                holder.name.isEnabled = false
            }
        }
        holder.name.setText(films.get(position).title)

        holder.image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(mContext, FilmDetailActivity::class.java)
                intent.putExtra("index", films.get(position).id)
                if (offline) {
                    intent.putExtra("mode", true)
                } else {
                    intent.putExtra("mode", false)
                }
                startActivity(mContext, intent, null)
            }
        })
    }

    override fun getItemCount(): Int {
        return films.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var image: ImageView
        internal var name: TextView

        init {
            image = itemView.findViewById(R.id.image_card_film)
            name = itemView.findViewById(R.id.name_card_film)

        }
    }


}
