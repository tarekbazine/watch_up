package com.example.tarekbaz.watch_up

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_home.*
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.drawer_activity.*


class HomeActivity : BaseActivity() {

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



    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        home_film_slider.setLayoutManager(layoutManager)
        val adapter_films = HomeRecyclerViewAdapter(this, filmNames , imageFilmsUrls)
        home_film_slider.setAdapter(adapter_films)

        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        home_serie_slider.setLayoutManager(layoutManager2)
        val adapter_series = HomeRecyclerViewAdapter(this, serieNames , imageSerieUrls)
        home_serie_slider.setAdapter(adapter_series)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        //Add drawer button
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        // init RecyclerViews
        this.initRecyclerView()
    }

}
