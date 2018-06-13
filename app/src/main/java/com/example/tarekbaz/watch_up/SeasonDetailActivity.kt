package com.example.tarekbaz.watch_up

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.widget.MediaController
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.tarekbaz.watch_up.Adapters.CommentRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.EpisodeRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Models.*
import com.example.tarekbaz.watch_up.Models.Mocker.getRandomElements_
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_detail_season.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

class SeasonDetailActivity : AppCompatActivity() {
    //For testing detail season

    //Video attributes
    var trailer_video = R.raw.trailer1
    private var mediaController: MediaController? = null
    private var positionVideo: Int = 0



    private fun initEpisodesRecyclerView(episodes : List<Episode>, index:Int , indexSerie :Int) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        episodesRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = EpisodeRecyclerViewAdapter(this, episodes, index, indexSerie)
        episodesRecyclerView.setAdapter(adapter_films)
    }

    private fun initCommentsSeasonRecyclerView(comments : List<Comment>) {
        val layoutManager = LinearLayoutManager(this)
        commentsSeasonRecyclerView.setLayoutManager(layoutManager)
        val adapter_comments = CommentRecyclerViewAdapter(this, comments)
        commentsSeasonRecyclerView.setAdapter(adapter_comments)
    }

    var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_season)
        setSupportActionBar(toolbar_detail_season)
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }

        index = intent.extras.getInt("index",0)
        val indexSerie = intent.extras.getInt("indexSerie",0)

        var serie = Store.homeSeries[0]
        Store.homeSeries.forEach { it ->
            if (it.id == indexSerie)
                serie = it
        }

        val comments = Mocker.commentList.getRandomElements_(4)
        val season = serie.seasons[index]

//        Log.i("myLogi",""+serie.id +""+ indexSerie)
//        Log.i("myLogi",""+serie.seasons)
        val glide = Glide.with(this)

        glide.load(Config.IMG_BASE_URL + season.poster_path)
                .into(seasonCard)

        glide.load(Config.IMG_BASE_URL + season.poster_path)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable,
                                                 transition: Transition<in Drawable>?) {
                        frameLayout.setBackground(resource)
                    }
                })

        serieTitle.text = season.name
        descriptionText.text = season.discription

        serieDate.text = "(${SimpleDateFormat("yyyy").format(serie.first_air_date)})"
        evaluationText.text = serie.evaluation.toString()
//        actorsNamesText.text = season.linkedActors.get(0).name

        //Set title
        toolbar_detail_season.title = serieTitle.text

        //Init trailer video
        initTrailer(trailer_video)

        //Hide Media controller when scrolling
        scrollContainer.getViewTreeObserver().addOnScrollChangedListener(
                ViewTreeObserver.OnScrollChangedListener {
                    mediaController!!.hide()
                })


        initDetailSerieDataAPI(indexSerie,season)
        this.initCommentsSeasonRecyclerView(comments)
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

    fun initDetailSerieDataAPI(serieId : Int,season : Season){

        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val service = retrofit.create<Service>(Service::class.java!!)


        service.seasonDetails(serieId,index).enqueue(object: Callback<Season> {
            override fun onResponse(call: Call<Season>, response: retrofit2.Response<Season>?) {
                if ((response != null) && (response.code() == 200)) {
                    season.episodes = response.body()!!.episodes

                    episodesNumber.text = "${season.episodes.size} épesodes"
                    initEpisodesRecyclerView(season.episodes,index,serieId)
                }
            }
            override fun onFailure(call: Call<Season>?, t: Throwable?){
                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
            }
        })

    }

}