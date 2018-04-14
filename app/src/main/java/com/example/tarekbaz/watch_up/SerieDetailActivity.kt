package com.example.tarekbaz.watch_up

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import kotlinx.android.synthetic.main.activity_detail_serie.*
import kotlinx.android.synthetic.main.activity_series.*

class SerieDetailActivity : AppCompatActivity() {
    //For testing detail film
    //Associated Films
    val namesSeason: List<String> = mutableListOf(
            "Saison1", "Saison2"
    )

    val imageSeasonUrls: List<Int> = mutableListOf(
            R.drawable.film4, R.drawable.film5
    )

    //Associated Films
    val associatedSeriesNames: List<String> = mutableListOf(
            "La Belle et La Bète", "Hunger Game", "Drone"
    )

    val associatedSeriesUrls: List<Int> = mutableListOf(
            R.drawable.film4, R.drawable.film5, R.drawable.serie1
    )

    //Comments
    val userNames: List<String> = mutableListOf(
            "Cinema Paris Salle 0012", "Larousse Cinema",  "Cinema des Rois -Marseille-"
    )

    val commentDates: List<String> = mutableListOf(
            "7/7 de 8:00 à 23:00", "24/24 sauf samedi de 8:00 à 23:00","Toujours 10:00 à 23:00","de 8:00 à 20:00 sauf lundi"
    )

    val userImages: List<Int> = mutableListOf(
            R.drawable.film4, R.drawable.film5, R.drawable.serie1, R.drawable.film5
    )

    val comments: List<String> = mutableListOf(
            "Space excelsive", "From mars", "Live from the sea", "Directly from desert"
    )



    private fun initSeasonsRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        seasonsRecyclerView.setLayoutManager(layoutManager)
        val adapter_seasons= HomeRecyclerViewAdapter(this, namesSeason, imageSeasonUrls)
        seasonsRecyclerView.setAdapter(adapter_seasons)
    }

    private fun initAssociatedSeriesRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        associatedSeriesRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = HomeRecyclerViewAdapter(this, associatedSeriesNames, associatedSeriesUrls)
        associatedSeriesRecyclerView.setAdapter(adapter_films)
    }

    private fun initCommentsRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        commentsSerieRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = CommentRecyclerViewAdapter(this, userNames , userImages,comments ,commentDates)
        commentsSerieRecyclerView.setAdapter(adapter_films)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_serie)
        this.initSeasonsRecyclerView()
        this.initAssociatedSeriesRecyclerView()
        this.initCommentsRecyclerView()
    }
}