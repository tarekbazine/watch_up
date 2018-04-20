package com.example.tarekbaz.watch_up

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_detail_film.*
import kotlinx.android.synthetic.main.activity_detail_season.*

class SeasonDetailActivity : AppCompatActivity() {
    //For testing detail season

    //Video attributes
    var trailer_video = R.raw.trailer1
    private var mediaController: MediaController? = null
    private var positionVideo: Int = 0


    val serieName = "La Casa De Papel"
    val namesEpisode: List<String> = mutableListOf(
            "episode1", "episode2", "episode3"
    )

    val imagesEpisodenUrls: List<Int> = mutableListOf(
            R.drawable.film4, R.drawable.film5, R.drawable.film2
    )

    //Comments
    val userNames: List<String> = mutableListOf(
            "Cinema Paris Salle 0012", "Larousse Cinema", "Cinema des Rois -Marseille-"
    )

    val commentDates: List<String> = mutableListOf(
            "7/7 de 8:00 à 23:00", "24/24 sauf samedi de 8:00 à 23:00", "Toujours 10:00 à 23:00", "de 8:00 à 20:00 sauf lundi"
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
        val adapter_comments = CommentRecyclerViewAdapter(this, userNames, comments, commentDates)
        commentsSeasonRecyclerView.setAdapter(adapter_comments)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_season)
        setSupportActionBar(toolbar_detail_season)
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }
        //Set title
        toolbar_detail_season.title = serieName

        //Init trailer video
        initTrailer(trailer_video)

        //Hide Media controller when scrolling
        scrollContainer.getViewTreeObserver().addOnScrollChangedListener(
                ViewTreeObserver.OnScrollChangedListener {
                    mediaController!!.hide()
                })

        this.initEpisodesRecyclerView()
        this.initCommentsSeasonRecyclerView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initTrailer(idTrailer: Int) {
        try {
            // ID of video file.
            val uri = Uri.parse("android.resource://" + getPackageName() + "/" + idTrailer)
            trailerVideoSeason.setVideoURI(uri)
        } catch (e: Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }

        // Set the media controller buttons
        if (mediaController == null) {
            mediaController = MediaController(this@SeasonDetailActivity)
        }
        trailerVideoSeason.setMediaController(mediaController)


        trailerVideoSeason.setOnPreparedListener { mediaPlayer ->
            if (this.positionVideo == 0) {
                trailerVideoSeason.seekTo(10000)
            } else {
                trailerVideoSeason.seekTo(positionVideo)
            }
            mediaController!!.setAnchorView(trailerVideoSeason)

            // When video Screen change size.
            mediaPlayer.setOnVideoSizeChangedListener { mp, width, height ->
                // Re-Set the videoView that acts as the anchor for the MediaController
                mediaController!!.setAnchorView(trailerVideoSeason)
            }
        }


        // Change image preview
        trailerVideoSeason.setPlayPauseListener (object : CustomVideoView.PlayPauseListener {
            override fun onPlay() {
                trailerImageSeason.visibility = View.GONE
            }
            override fun onPause() {
                trailerImageSeason.visibility = View.VISIBLE
                positionVideo = trailerVideoSeason.getCurrentPosition()
            }
            override fun onTouch() {
                if (! trailerVideoSeason.isPlaying){
                    trailerVideoSeason.start()
                }
            }
        })
    }

    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    public override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        if (trailerVideoSeason.isPlaying){
            savedInstanceState!!.putBoolean("VideoIsPlaying", true)
            trailerVideoSeason.pause()
        }else{
            savedInstanceState!!.putBoolean("VideoIsPlaying", false)
        }
        // Store current video position.
        savedInstanceState!!.putInt("CurrentPosition", positionVideo)
    }

    // After rotating the phone. This method is called.
    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Get saved video position.
        this.positionVideo = savedInstanceState.getInt("CurrentPosition")
        val isPlaying = savedInstanceState.getBoolean("VideoIsPlaying")
        if (isPlaying){
            trailerVideoSeason.start()
        }
    }
}