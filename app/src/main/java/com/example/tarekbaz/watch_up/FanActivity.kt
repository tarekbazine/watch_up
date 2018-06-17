package com.example.tarekbaz.watch_up

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.tarekbaz.watch_up.Adapters.HomeMovieRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.HomeSerieRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.SalleFanRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Models.AssotiationMovies
import com.example.tarekbaz.watch_up.Models.Mocker
import com.example.tarekbaz.watch_up.Models.Movie
import com.example.tarekbaz.watch_up.Models.Store
import com.example.tarekbaz.watch_up.Offline.MovieDAO
import com.example.tarekbaz.watch_up.Offline.MovieDB
import com.example.tarekbaz.watch_up.Offline.RelatedMoviesDAO
import com.example.tarekbaz.watch_up.R.drawable.film
import kotlinx.android.synthetic.main.activity_fan.*
import kotlinx.android.synthetic.main.drawer_activity.*


class FanActivity : BaseActivity() {

    private var db: MovieDB? = null
    private var movieDao: MovieDAO? = null
    private var relatedMovieDao: RelatedMoviesDAO? = null
    private var fanFilms : List<Movie>? = null

    //TODO USE Models
    val filmNames: List<String> = mutableListOf(
            "La Belle et La Bète", "Hunger Game", "Drone" , "Hunger Game 2"
    )

    val imageFilmsUrls: List<Int> = mutableListOf(
            R.drawable.film4,R.drawable.film5,R.drawable.serie1,R.drawable.film5
    )

    val serieNames: List<String> = mutableListOf(
            "Le throne de Fer", "LaCasa de Papel", "Breaking Bad" , "LaCasa de Papel"
    )

    val imageSerieUrls: List<Int> = mutableListOf(
            R.drawable.film1,R.drawable.serie2,R.drawable.film3,R.drawable.serie2
    )


//    val fanFilms = Mocker.favMovieList
    val fanSeries = Mocker.favSerieList
    val fanSalles = Mocker.favCinemaList

    var adapter_films :HomeMovieRecyclerViewAdapter? = null
    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        fan_film_slider.setLayoutManager(layoutManager)
        adapter_films = HomeMovieRecyclerViewAdapter(this, fanFilms!!,true)
        fan_film_slider.setAdapter(adapter_films)

        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        fan_serie_slider.setLayoutManager(layoutManager2)
        val adapter_series = HomeSerieRecyclerViewAdapter(this, fanSeries)
        fan_serie_slider.setAdapter(adapter_series)

        val layoutManager3 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        fan_salle_slider.setLayoutManager(layoutManager3)
        val adapter_salles = SalleFanRecyclerViewAdapter(this, fanSalles)
        fan_salle_slider.setAdapter(adapter_salles)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fan)
        setSupportActionBar(toolbar)

        //Add drawer button
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        initDB()

    }

    override fun onRestart() {
        super.onRestart()
        refreshFav()
    }

    fun initDB() {
        var act = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                act.db = MovieDB.getInstance(act)
                act.movieDao = act.db!!.movieDAO()
                act.relatedMovieDao = act.db!!.relatedMoviesDAO()
                act.fanFilms = act.movieDao?.getFavMovies()
                Store.favFilms = act.fanFilms as ArrayList<Movie>
                act.initRecyclerView()
                return null
            }

            override fun onPostExecute(result: Void?) {
                act.associateMovies()
                Log.i("bd","bd created")
            }
        }.execute()
    }

    fun refreshFav() {
//            fanFilms =  Store.favFilms
        adapter_films!!.notifyDataSetChanged()
    }

    fun associateMovies() {
        var act = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                for(movie in act.fanFilms!!){
                    val associatedMovies = act.relatedMovieDao?.getRelatedMovies(movie.id)
                    movie.linkedMovies = associatedMovies
                    Log.i("related", movie.linkedMovies!!.toString())
                }
                return null
            }

            override fun onPostExecute(result: Void?) {
                Store.favFilms = act.fanFilms as ArrayList<Movie>
                act.initRecyclerView()
                Log.i("bd","bd created")
            }
        }.execute()
    }




}