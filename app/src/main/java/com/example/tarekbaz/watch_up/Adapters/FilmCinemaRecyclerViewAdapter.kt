package com.example.tarekbaz.watch_up.Adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tarekbaz.watch_up.API.Responses.ListPaginatedResponse
import com.example.tarekbaz.watch_up.API.Service
import com.example.tarekbaz.watch_up.Config
import com.example.tarekbaz.watch_up.FilmDetailActivity
import com.example.tarekbaz.watch_up.Models.Movie
import com.example.tarekbaz.watch_up.Models.Store
import com.example.tarekbaz.watch_up.R
import com.example.tarekbaz.watch_up.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

class FilmCinemaRecyclerViewAdapter(private val mContext: Context, var films: List<Movie>) : RecyclerView.Adapter<FilmCinemaRecyclerViewAdapter.ViewHolder>() {

    var fullFilms = films
    var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_film, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.film_name.text = films.get(position).title

        Glide.with(mContext)
                .load(Config.IMG_BASE_URL + films.get(position).poster_path)
                .into(holder.film_image)

        if (films.get(position).release_date != null) {
            holder.film_date.text = SimpleDateFormat("E dd MMM yyyy").format(films.get(position).release_date)
        } else {
            holder.film_date.text = "Acune date est specifie"
        }

        holder.film_rating.rating = films.get(position).vote_average.toFloat()

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                val intent = Intent(mContext, FilmDetailActivity::class.java)
                intent.putExtra("index", films.get(position).id)
                Store.homeFilms.add(films.get(position))
                ContextCompat.startActivity(mContext, intent, null)

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
        return this.films.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var film_name: TextView
        //            internal var film_realisator: TextView
//            internal var film_salle: TextView
        internal var film_date: TextView
        internal var film_rating: RatingBar
        internal var film_image: ImageView

        init {
            film_name = itemView.findViewById(R.id.film_name)
//                film_realisator = itemView.findViewById(R.id.film_realisator)
//                film_salle = itemView.findViewById(R.id.film_salle)
            film_date = itemView.findViewById(R.id.film_date)
            film_rating = itemView.findViewById(R.id.film_rating)
            film_image = itemView.findViewById(R.id.film_image)
        }
    }


    fun filter(keyWords: String) {
        if (keyWords.isEmpty()) {
            films = fullFilms //todo
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

        service.searchMovies(query).enqueue(object : Callback<ListPaginatedResponse<Movie>> {

            override fun onResponse(call: Call<ListPaginatedResponse<Movie>>,
                                    response: Response<ListPaginatedResponse<Movie>>?) {
                if ((response != null) && (response.code() == 200)) {
                    films = response.body()!!.results
                    notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ListPaginatedResponse<Movie>>?, t: Throwable?) {
                Toast.makeText(mContext, "Echec", Toast.LENGTH_LONG).show()
//                Log.i("myLogapi2", "" + t?.stackTrace)
            }
        })

    }
}