package com.example.tarekbaz.watch_up

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import kotlinx.android.synthetic.main.activity_detail_film.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import android.view.ViewTreeObserver
import com.bumptech.glide.Glide
import com.example.tarekbaz.watch_up.Adapters.CommentRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.HomeMovieRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.SalleRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Models.*
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.tarekbaz.watch_up.Models.Mocker.getRandomElements
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.MoviesResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FilmDetailActivity : AppCompatActivity() {

    var str1 = "22:03"
    var str2 = "22:30"
    var formatter: DateFormat = SimpleDateFormat("HH:mm")
    var date1 = formatter.parse(str1)
    var date2 = formatter.parse(str2)
    var is_fan = false

    //Video attributes
    var trailer_video = R.raw.trailer2
    private var mediaController: MediaController? = null
    private var positionVideo: Int = 0

    val filmsTimes: List<Date> = mutableListOf(
            date1, date2, date1
    )


    //Init adapters
    private fun initSallesRecyclerView(salles: List<Cinema>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sallesRecyclerView.setLayoutManager(layoutManager)//todo
        val adapter_salles = SalleRecyclerViewAdapter(this, salles)
        sallesRecyclerView.setAdapter(adapter_salles)
    }

    private fun initAssociatedFilmsRecyclerView(assFilms: List<Movie>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        associatedFilmsRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = HomeMovieRecyclerViewAdapter(this, assFilms)
        associatedFilmsRecyclerView.setAdapter(adapter_films)
    }

    private fun initCommentsRecyclerView(comments: List<Comment>) {
        val layoutManager = LinearLayoutManager(this)
        commentsFilmRecyclerView.setLayoutManager(layoutManager)
        val adapter_comments = CommentRecyclerViewAdapter(this, comments)
        commentsFilmRecyclerView.setAdapter(adapter_comments)
    }

    var film = Store.homeFilms[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        val index = intent.extras.getInt("index", 0)

        Store.homeFilms.forEach { it ->
            if (it.id == index)
                film = it
        }

        initDetailFilmDataAPI(film.id)

        val salles = Mocker.salleList.getRandomElements(4)
        film.cinemas = salles

        val comments = Mocker.commentList.getRandomElements(4)
        film.comments = comments

        //todo fav
        Mocker.favMovieList.forEach { it ->
            if (it.id == film.id)
                is_fan = true
        }

        val glide = Glide.with(this)

        glide.load(Config.IMG_BASE_URL + film.poster_path)
                .into(filmCard)
        filmTitle.text = film.title
        glide.load(Config.IMG_BASE_URL + film.poster_path)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable,
                                                 transition: Transition<in Drawable>?) {
                        frameLayout.setBackground(resource)
                    }
                })

        evaluationText.text = film.vote_average.toString()

        descriptionText.text = film.description

        filmDate.text = "(${SimpleDateFormat("yyyy").format(film.release_date)})"


        //todo
//        actors_names.text = film.actors.get(0).name
//        producertext.text = film.directors.get(0).name

        setSupportActionBar(toolbar_detail_film)
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }
        //Set activity title
        toolbar_detail_film.title = film.title

        //Init trailer video
        initTrailer(trailer_video)

        //Hide Media controller when scrolling
        scrollContainer.getViewTreeObserver().addOnScrollChangedListener(
                ViewTreeObserver.OnScrollChangedListener {
                    mediaController!!.hide()
                })

        initSallesRecyclerView(salles)
        initCommentsRecyclerView(comments)
    }

    //Add search view
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_love, menu)

        //Set fun heart icon for the first time
        if (this.is_fan) {
            this.is_fan = false
            menu.findItem(R.id.love_item).setIcon(R.drawable.heart2)
        } else {
            this.is_fan = true
            menu.findItem(R.id.love_item).setIcon(R.drawable.heart_inactive)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.love_item) {
            if (this.is_fan) {
                this.is_fan = false
                item.setIcon(R.drawable.heart2)
                Mocker.favMovieList.add(film)
                Toast.makeText(this, "Film ajouté à Mes Fans", LENGTH_SHORT).show()
            } else {
                this.is_fan = true
                item.setIcon(R.drawable.heart_inactive)
                Mocker.favMovieList.remove(film)
                Toast.makeText(this, "Film enlevé de Mes Fans", LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initTrailer(idTrailer: Int) {
        try {
            // ID of video file.
            val uri = Uri.parse("android.resource://" + getPackageName() + "/" + idTrailer)
            trailerVideo.setVideoURI(uri)
        } catch (e: Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }

        // Set the media controller buttons
        if (mediaController == null) {
            mediaController = MediaController(this@FilmDetailActivity)
        }
        trailerVideo.setMediaController(mediaController)


        trailerVideo.setOnPreparedListener { mediaPlayer ->
            if (this.positionVideo == 0) {
                trailerVideo.seekTo(10000)
            } else {
                trailerVideo.seekTo(positionVideo)
            }
            mediaController!!.setAnchorView(trailerVideo)

            // When video Screen change size.
            mediaPlayer.setOnVideoSizeChangedListener { mp, width, height ->
                // Re-Set the videoView that acts as the anchor for the MediaController
                mediaController!!.setAnchorView(trailerVideo)
            }

        }

        // Change image preview
        trailerVideo.setPlayPauseListener(object : CustomVideoView.PlayPauseListener {
            override fun onPlay() {
                trailerImage.visibility = View.GONE
            }

            override fun onPause() {
                trailerImage.visibility = View.VISIBLE
                positionVideo = trailerVideo.getCurrentPosition()
            }

            override fun onTouch() {
                if (!trailerVideo.isPlaying) {
                    trailerVideo.start()
                }
            }
        })
    }


    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    public override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        if (trailerVideo.isPlaying) {
            savedInstanceState!!.putBoolean("VideoIsPlaying", true)
            trailerVideo.pause()
        } else {
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
        if (isPlaying) {
            trailerVideo.start()
        }
    }

    fun initDetailFilmDataAPI(movieId : Int){

        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val service = retrofit.create<Service>(Service::class.java!!)

        service.relatedMovies(movieId).enqueue(object: Callback<MoviesResponse> {

            override fun onResponse(call: Call<MoviesResponse>, response: retrofit2.Response<MoviesResponse>?) {
                if ((response != null) && (response.code() == 200)) {
                    val relatedMovies = response.body()!!.results
                    film.linkedMovies = relatedMovies
                    initAssociatedFilmsRecyclerView(relatedMovies)
                }

            }

            override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?){
                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
            }
        })

//        service.getTodayAiringSeries().enqueue(object: Callback<SeriesResponse> {
//            override fun onResponse(call: Call<SeriesResponse>, response: retrofit2.Response<SeriesResponse>?) {
//                if ((response != null) && (response.code() == 200)) {
//                    val series = response.body()!!.results
//                    Store.homeSeries = series
//                    initSerieRecyclerView(series)
//                }
//            }
//            override fun onFailure(call: Call<SeriesResponse>?, t: Throwable?){
//                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
//            }
//        })

    }

}
