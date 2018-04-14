package com.example.tarekbaz.watch_up

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_cinema.*
import kotlinx.android.synthetic.main.activity_detail_film.*

import kotlinx.android.synthetic.main.activity_series.*
import kotlinx.android.synthetic.main.drawer_activity.*

class SeriesActivity  : BaseActivity()  {

    //TODO USE Models
    val filmNames: List<String> = mutableListOf(
            "La Belle et La Bète", "Hunger Game", "Drone", "Hunger Game 2"
    )

    val imageFilmsUrls: List<Int> = mutableListOf(
            R.drawable.film4, R.drawable.film5, R.drawable.serie1, R.drawable.film5
    )

    val filmDirectors: List<String> = mutableListOf(
            "Le throne de Fer", "LaCasa de Papel", "Breaking Bad", "LaCasa de Papel"
    )

    private fun initSerieCardsRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        seriesRecyclerView.setLayoutManager(layoutManager)
        val adapter_series = SeriesRecyclerViewAdapter(this, filmNames , imageFilmsUrls, filmDirectors)
        seriesRecyclerView.setAdapter(adapter_series)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series)
        setSupportActionBar(toolbar_series)
        //Add drawer button
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar_series, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        initSerieCardsRecyclerView()
    }

}
