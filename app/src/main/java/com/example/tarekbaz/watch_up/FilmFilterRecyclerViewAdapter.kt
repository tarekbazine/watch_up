package com.example.tarekbaz.watch_up

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class FilmFilterRecyclerViewAdapter(private val mContext: Context, val filmNames: List<String>,
                                    val imageFilmsUrls: List<Int>, val filmDirectors: List<String>,
                                    val filmCinema: List<String>) : RecyclerView.Adapter<FilmFilterRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_film, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.film_image.setImageResource(imageFilmsUrls.get(position))
        holder.film_name.text = filmCinema.get(position)
        holder.film_realisator.setText(filmDirectors.get(position))
        holder.film_salle.setText(filmCinema.get(position))
//            var formatter: DateFormat = SimpleDateFormat("HH:mm")
//            holder.film_date.setText(formatter.format(times!!.get(position)))


        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (mContext is CommentEvaluationActivity) {
                        mContext.mSectionsPagerAdapter!!.switchFilmFragmentToCommentView()
                }

            }
        })
    }

    override fun getItemCount(): Int {
        return this.filmNames.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var film_name: TextView
        internal var film_realisator: TextView
        internal var film_salle: TextView
        internal var film_date: TextView
        internal var film_image: ImageView

        init {
            film_name = itemView.findViewById(R.id.film_name)
            film_realisator = itemView.findViewById(R.id.film_realisator)
            film_salle = itemView.findViewById(R.id.film_salle)
            film_date = itemView.findViewById(R.id.film_date)
            film_image = itemView.findViewById(R.id.film_image)
        }
    }
}