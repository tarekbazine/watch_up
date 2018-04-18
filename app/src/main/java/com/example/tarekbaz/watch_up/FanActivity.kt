package com.example.tarekbaz.watch_up

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_fan.*
import kotlinx.android.synthetic.main.drawer_activity.*


class FanActivity : BaseActivity() {

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
        fan_film_slider.setLayoutManager(layoutManager)
        val adapter_films = HomeRecyclerViewAdapter(this, filmNames , imageFilmsUrls)
        fan_film_slider.setAdapter(adapter_films)

        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        fan_serie_slider.setLayoutManager(layoutManager2)
        val adapter_series = HomeRecyclerViewAdapter(this, serieNames , imageSerieUrls)
        fan_serie_slider.setAdapter(adapter_series)

        val layoutManager3 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        fan_salle_slider.setLayoutManager(layoutManager3)
        val adapter_salles = HomeRecyclerViewAdapter(this, filmNames , imageFilmsUrls)
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


        // init RecyclerViews
        this.initRecyclerView()
    }

}