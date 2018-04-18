package com.example.tarekbaz.watch_up

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class CommentBySeriesFilterRecyclerViewAdapter(private val mContext: Context, var serieNames: List<String>,
                                               val imageSeriesUrls: List<Int>, val serieDirectors: List<String>) : RecyclerView.Adapter<CommentBySeriesFilterRecyclerViewAdapter.ViewHolder>() {

    val fullSerie = serieNames

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_film, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.serie_image.setImageResource(imageSeriesUrls.get(position))
        holder.serie_name.text = serieNames.get(position)
        holder.serie_realisator.setText(serieDirectors.get(position))

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (mContext is CommentEvaluationActivity) {
                    mContext.mSectionsPagerAdapter!!.switchSerieFragmentToCommentView()
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return this.serieNames.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var serie_name: TextView
        internal var serie_realisator: TextView
        internal var serie_image: ImageView

        init {
            serie_name = itemView.findViewById(R.id.film_name)
            serie_realisator = itemView.findViewById(R.id.film_realisator)
            serie_image = itemView.findViewById(R.id.film_image)
        }
    }

    fun filter(keyWords: String) {
        serieNames = fullSerie.filter { serieName -> serieName.contains(keyWords, true) }
        notifyDataSetChanged()
    }
}