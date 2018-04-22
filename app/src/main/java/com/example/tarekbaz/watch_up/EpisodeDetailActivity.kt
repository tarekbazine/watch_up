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
import com.example.tarekbaz.watch_up.Models.Comment
import com.example.tarekbaz.watch_up.Models.Mocker
import com.example.tarekbaz.watch_up.Models.Serie
import kotlinx.android.synthetic.main.activity_detail_episode.*
import kotlinx.android.synthetic.main.activity_detail_personne.*


class EpisodeDetailActivity : AppCompatActivity() {


    val length = "45min"


    private fun initCommentsRecyclerView(comments :List<Comment>) {
        val layoutManager = LinearLayoutManager(this)
        commentsEpisodeRecyclerView.setLayoutManager(layoutManager)
        val adapter_comments = CommentRecyclerViewAdapter(this, comments)
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

        val index = intent.extras.getInt("index",0)
        val indexSeason = intent.extras.getInt("indexSeason",0)
        val indexSerie = intent.extras.getInt("indexSerie",0)


        var serie: Serie = Mocker.serieList[0]
        Mocker.serieList.forEach { it ->
            if (it.id == indexSerie)
                serie = it
        }

        val comments = serie.seasons[indexSeason].epesods[index].comments
        val episode = serie.seasons[indexSeason].epesods[index]


        //hide existing canal
        canalsLayout.removeView(chain1)
        for (i in 0 until episode.diffusion.size){
            val canal = TextView(this)
            canal.textSize = 17F
            canal.setTextColor(Color.WHITE)
            canal.text =(episode.diffusion[i])
            canalsLayout.addView(canal)
        }

        episodeCard.setImageResource(R.drawable.film3)
        serieTitleText.text = "Episode "+ (index+1)
        descriptionEpisodeText.text = episode.discription
        durationText.text = length

        this.initCommentsRecyclerView(comments)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}