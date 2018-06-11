package com.example.tarekbaz.watch_up

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.tarekbaz.watch_up.Adapters.CommentRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.HomeSerieRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.SeasonRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Models.*
import com.example.tarekbaz.watch_up.Models.Mocker.getRandomElements_
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.SeriesResponse
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_detail_serie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import com.google.gson.Gson
import java.util.*


class SerieDetailActivity : AppCompatActivity() {

    var is_fan = false

    private fun initSeasonsRecyclerView(seasons: List<Season>, index: Int) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        seasonsRecyclerView.setLayoutManager(layoutManager)
        val adapter_seasons = SeasonRecyclerViewAdapter(this, seasons, index)
        seasonsRecyclerView.setAdapter(adapter_seasons)
    }

    private fun initAssociatedSeriesRecyclerView(assSerie: List<Serie>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        associatedSeriesRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = HomeSerieRecyclerViewAdapter(this, assSerie)
        associatedSeriesRecyclerView.setAdapter(adapter_films)
    }

    private fun initCommentsRecyclerView(comments: List<Comment>) {
        val layoutManager = LinearLayoutManager(this)
        commentsSerieRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = CommentRecyclerViewAdapter(this, comments)
        commentsSerieRecyclerView.setAdapter(adapter_films)
    }


    //Add search view
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_love, menu)

        //Set fun heart icon for the first time
        if (this.is_fan) {
            this.is_fan = false
            menu.findItem(R.id.love_item).setIcon(R.drawable.heart2)
        } else {
            this.is_fan = true
            menu.findItem(R.id.love_item).setIcon(R.drawable.heart_inactive)
        }
        return true
    }

    var serie: Serie = Store.homeSeries[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_serie)

        val serieId = intent.extras.getInt("_id", 0)

        Store.homeSeries.forEach { it ->
            if (it.id == serieId)
                serie = it
        }

        Log.i("mylog", "" + serie.id + " " + serieId)
        val comments = Mocker.commentList.getRandomElements_(4)
//        serie.comments = comments

        //todo fav
        Mocker.favSerieList.forEach { it ->
            if (it.id == serie.id)
                is_fan = true
        }

        val glide = Glide.with(this)

        glide.load(Config.IMG_BASE_URL + serie.poster_path)
                .into(filmCard)

        glide.load(Config.IMG_BASE_URL + serie.poster_path)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable,
                                                 transition: Transition<in Drawable>?) {
                        frameLayout.setBackground(resource)
                    }
                })

        serieTitle.text = serie.title
        descriptionText.text = serie.discription
        if (serie.first_air_date != null) {
            serieDate.text = "(${SimpleDateFormat("yyyy").format(serie.first_air_date)})"
        }
        evaluationText.text = serie.evaluation.toString()

        initCommentsRecyclerView(comments)
        initDetailSerieDataAPI(serie.id)

        setSupportActionBar(toolbar_detail_serie)
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }
        //Set activity title
        toolbar_detail_serie.title = serie.title

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.love_item) {
            if (this.is_fan) {
                this.is_fan = false
                item.setIcon(R.drawable.heart2)
                Mocker.favSerieList.add(serie)
                Toast.makeText(this, "Série ajoutée à Mes Fans", Toast.LENGTH_SHORT).show()
            } else {
                this.is_fan = true
                item.setIcon(R.drawable.heart_inactive)
                Mocker.favSerieList.remove(serie)
                Toast.makeText(this, "Série enlevée de Mes Fans", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun initDetailSerieDataAPI(serieId: Int) {

        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val service = retrofit.create<Service>(Service::class.java!!)


        service.serieDetails(serieId).enqueue(object : Callback<Serie> {
            override fun onResponse(call: Call<Serie>, response: retrofit2.Response<Serie>?) {
                if ((response != null) && (response.code() == 200)) {
                    serie.seasons = response.body()!!.seasons
                    serie.episode_run_time = response.body()!!.episode_run_time

                    initSeasonsRecyclerView(serie.seasons, serie.id)
                }
            }

            override fun onFailure(call: Call<Serie>?, t: Throwable?) {
                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
            }
        })

        service.relatedSeries(serieId).enqueue(object : Callback<SeriesResponse> {

            override fun onResponse(call: Call<SeriesResponse>, response: retrofit2.Response<SeriesResponse>?) {
                if ((response != null) && (response.code() == 200)) {
                    val relatedSeriers = response.body()!!.results
                    serie.linkedSeries = relatedSeriers
                    relatedSeriers.forEach { it ->
                        Store.homeSeries.add(it)
                    }
                    initAssociatedSeriesRecyclerView(relatedSeriers)
                }

            }

            override fun onFailure(call: Call<SeriesResponse>?, t: Throwable?) {
                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
            }
        })

    }

}