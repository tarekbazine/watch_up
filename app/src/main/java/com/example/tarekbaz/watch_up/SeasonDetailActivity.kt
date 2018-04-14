package com.example.tarekbaz.watch_up

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_season.*

class SeasonDetailActivity : AppCompatActivity() {
    //For testing detail season
    //Associated Films
    val namesEpisode: List<String> = mutableListOf(
            "episode1", "episode2", "episode3"
    )

    val imagesEpisodenUrls: List<Int> = mutableListOf(
            R.drawable.film4, R.drawable.film5,  R.drawable.film2
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


    private fun initEpisodesRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        episodesRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = HomeRecyclerViewAdapter(this, namesEpisode, imagesEpisodenUrls)
        episodesRecyclerView.setAdapter(adapter_films)
    }

    private fun initCommentsSeasonRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        commentsSeasonRecyclerView.setLayoutManager(layoutManager)
        val adapter_comments = CommentRecyclerViewAdapter(this, userNames , userImages,comments ,commentDates)
        commentsSeasonRecyclerView.setAdapter(adapter_comments)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_season)
        this.initEpisodesRecyclerView()
        this.initCommentsSeasonRecyclerView()
    }
}