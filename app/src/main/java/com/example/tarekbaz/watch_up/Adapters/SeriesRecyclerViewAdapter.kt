package com.example.tarekbaz.watch_up.Adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tarekbaz.watch_up.Config
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.MoviesResponse
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.SeriesResponse
import com.example.tarekbaz.watch_up.Models.Serie
import com.example.tarekbaz.watch_up.Models.Service
import com.example.tarekbaz.watch_up.Models.Store
import com.example.tarekbaz.watch_up.R
import com.example.tarekbaz.watch_up.SerieDetailActivity
import com.example.tarekbaz.watch_up.Utils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import com.google.gson.JsonParseException
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializer
import retrofit2.Response
import java.lang.reflect.Type
import java.text.ParseException


class SeriesRecyclerViewAdapter(private val mContext: Context, var series: List<Serie>) : RecyclerView.Adapter<SeriesRecyclerViewAdapter.ViewHolder>() {

    val fullSerie = series
    var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_film, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        Glide.with(mContext)
                .load(Config.IMG_BASE_URL + series.get(position).poster_path)
                .into(holder.serie_image)

        holder.serie_name.setText(series.get(position).title)

        if(series.get(position).first_air_date != null){
            holder.film_date.text = SimpleDateFormat("E dd MMM yyyy").format(series.get(position).first_air_date)
        }else {
            holder.film_date.text =  "Acune date est specifie"
        }

        holder.film_rating.rating = series.get(position).evaluation.toFloat()

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(mContext, SerieDetailActivity::class.java)
                intent.putExtra("_id", series.get(position).id)
                Store.homeSeries.add(series.get(position))
                startActivity(mContext, intent, null)
            }
        })

        // Animation
        val animation = AnimationUtils.loadAnimation(mContext,
                if (position > lastPosition)
                    R.anim.buttom_from_top
                else
                    R.anim.top_from_buttom)
        holder.itemView.startAnimation(animation)
        lastPosition = position
    }

    override fun getItemCount(): Int {
        return series.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var serie_name: TextView
        internal var serie_image: ImageView
        internal var film_date: TextView
        internal var film_rating: RatingBar

        init {
            serie_name = itemView.findViewById(R.id.film_name)
            serie_image = itemView.findViewById(R.id.film_image)
            film_rating = itemView.findViewById(R.id.film_rating)
            film_date = itemView.findViewById(R.id.film_date)
        }
    }

    fun filter(keyWords: String) {
        if (keyWords.isEmpty()) {
            series = fullSerie //todo
            notifyDataSetChanged()
        } else {
            getDataAPI(keyWords)
        }
    }

    fun getDataAPI(query: String) {

        val gson = Utils.getGson()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val service = retrofit.create<Service>(Service::class.java!!)

        service.searchSeries(query).enqueue(object : Callback<SeriesResponse> {

            override fun onResponse(call: Call<SeriesResponse>,
                                    response: Response<SeriesResponse>?) {
                if ((response != null) && (response.code() == 200)) {
                    series = response.body()!!.results
                    notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<SeriesResponse>?, t: Throwable?) {
                Toast.makeText(mContext, "Echec Search", Toast.LENGTH_LONG).show()
            }
        })

    }


}