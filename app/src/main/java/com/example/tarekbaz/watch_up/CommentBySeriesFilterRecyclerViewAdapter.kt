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
import com.example.tarekbaz.watch_up.Models.Serie

class CommentBySeriesFilterRecyclerViewAdapter(private val mContext: Context, var series: List<Serie>) : RecyclerView.Adapter<CommentBySeriesFilterRecyclerViewAdapter.ViewHolder>() {

    val fullSeries = series

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_film, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.serie_image.setImageResource(series.get(position).image)
        holder.serie_name.text = series.get(position).title
        holder.serie_realisator.setText("")

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (mContext is CommentEvaluationActivity) {
                    mContext.mSectionsPagerAdapter!!.switchSerieFragmentToCommentView()
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return series.size
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
        series = fullSeries.filter { serie -> serie.title.contains(keyWords, true) }
        notifyDataSetChanged()
    }
}