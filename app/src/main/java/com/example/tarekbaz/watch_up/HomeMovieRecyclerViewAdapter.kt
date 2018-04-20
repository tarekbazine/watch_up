package com.example.tarekbaz.watch_up

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.tarekbaz.watch_up.Models.Movie


class HomeMovieRecyclerViewAdapter(private val mContext: Context, val films: List<Movie>) : RecyclerView.Adapter<HomeMovieRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_home_film, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(films.get(position).image)
        holder.name.setText(films.get(position).title)

        holder.image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(mContext, FilmDetailActivity::class.java)
                intent.putExtra("index", position)
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
