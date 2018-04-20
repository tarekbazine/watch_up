package com.example.tarekbaz.watch_up

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_detail_episode.*
import kotlinx.android.synthetic.main.activity_detail_personne.*


class EpisodeDetailActivity : AppCompatActivity() {
    //TODO Replace with object
    val title = "episode title example"
    val length = "45min"
    val description = " L’application interactive que nous proposons est destinée à un large public incluant différentes catégories d’utilisateurs à commencer par des personnes à la recherche d’offres de voyages d’une part, et d’autre part des agences de voyages qui veulent poster leurs offres de voyages ou encore des particuliers qui souhaitent louer leurs biens immobiliers ou véhicules pour les vacances"
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


    private fun initCommentsRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        commentsEpisodeRecyclerView.setLayoutManager(layoutManager)
        val adapter_comments = CommentRecyclerViewAdapter(this, userNames ,comments ,commentDates)
        commentsEpisodeRecyclerView.setAdapter(adapter_comments)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_episode)

        setSupportActionBar(toolbar_detail_episode)
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }

        //Set title
        toolbar_detail_episode.title = title

        //hide existing canal
        canalsLayout.removeView(chain1)
        val canals: List<TextView> = mutableListOf(
             TextView(this), TextView(this)
        )
        for (i in 0..1 ){
            val canal = canals.get(i)
            canal.textSize = 17F
            canal.setTextColor(Color.WHITE)
            canal.text =("Canal"+i)
            canalsLayout.addView(canal)
        }

        episodeCard.setImageResource(R.drawable.film3)
        serieTitleText.text = title
        descriptionEpisodeText.text = description
        durationText.text = length

        this.initCommentsRecyclerView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}