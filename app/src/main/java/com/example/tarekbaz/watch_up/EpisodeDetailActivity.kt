package com.example.tarekbaz.watch_up

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.tarekbaz.watch_up.Adapters.CommentRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Models.Comment
import com.example.tarekbaz.watch_up.Models.Mocker
import com.example.tarekbaz.watch_up.Models.Mocker.getRandomElements
import com.example.tarekbaz.watch_up.Models.Serie
import com.example.tarekbaz.watch_up.Models.Store
import kotlinx.android.synthetic.main.activity_detail_episode.*


class EpisodeDetailActivity : AppCompatActivity() {


    private fun initCommentsRecyclerView(comments: List<Comment>) {
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
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }


        val index = intent.extras.getInt("index", 0)
        val indexSeason = intent.extras.getInt("indexSeason", 0)
        val indexSerie = intent.extras.getInt("indexSerie", 0)


        var serie = Store.homeSeries[0]
        Store.homeSeries.forEach { it ->
            if (it.id == indexSerie)
                serie = it
        }

        val comments = Mocker.commentList.getRandomElements(4)
        val episode = serie.seasons[indexSeason].episodes[index]

        Glide.with(this)
                .load(Config.IMG_BASE_URL + episode.still_path)
                .into(episodeCard)

        //hide existing canal
//        canalsLayout.removeView(chain1)
//        for (i in 0 until episode.diffusion.size) {
//            val canal = TextView(this)
//            canal.textSize = 17F
//            canal.setTextColor(Color.WHITE)
//            canal.text = (episode.diffusion[i])
//            canalsLayout.addView(canal)
//        }


        serieTitleText.text = episode.name
        descriptionEpisodeText.text = episode.discription
        durationText.text = "${serie.episode_run_time[0]} min"
        evaluationText.text = serie.evaluation.toString()

        toolbar_detail_episode.title = serieTitleText.text

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