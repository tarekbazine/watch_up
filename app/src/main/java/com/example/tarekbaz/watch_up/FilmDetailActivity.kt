package com.example.tarekbaz.watch_up

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import java.text.SimpleDateFormat
import android.view.ViewTreeObserver
import com.bumptech.glide.Glide
import com.example.tarekbaz.watch_up.Adapters.CommentRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.HomeMovieRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.SalleRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Models.*
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.tarekbaz.watch_up.Models.Mocker.getRandomElements_
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.MoviesResponse
import com.example.tarekbaz.watch_up.Offline.ImageManager
import com.example.tarekbaz.watch_up.Offline.MovieDAO
import com.example.tarekbaz.watch_up.Offline.MovieDB
import com.example.tarekbaz.watch_up.Offline.RelatedMoviesDAO
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.ReviewsResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


class FilmDetailActivity : AppCompatActivity() {

    //Video attributes
    var trailer_video = R.raw.trailer2
    private var mediaController: MediaController? = null
    private var positionVideo: Int = 0

    private var db: MovieDB? = null
    private var movieDao: MovieDAO? = null
    private var relatedMovieDao: RelatedMoviesDAO? = null
    private var relatedMovies: List<Movie>? = null
    private var favoriteMoviesId: ArrayList<Int>? = ArrayList()

    var loveItem : MenuItem? = null
    var glide: RequestManager? = null
    var index:Int? = null


    //Init adapters
    private fun initSallesRecyclerView(salles: List<Cinema>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sallesRecyclerView.setLayoutManager(layoutManager)//todo
        val adapter_salles = SalleRecyclerViewAdapter(this, salles)
        sallesRecyclerView.setAdapter(adapter_salles)
    }

    private fun initAssociatedFilmsRecyclerView(assFilms: List<Movie>, offline:Boolean=false) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        associatedFilmsRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = HomeMovieRecyclerViewAdapter(this, assFilms, offline)
        associatedFilmsRecyclerView.setAdapter(adapter_films)
    }

    private fun initCommentsRecyclerView(comments: List<Comment>) {
        val layoutManager = LinearLayoutManager(this)
        commentsFilmRecyclerView.setLayoutManager(layoutManager)
        val adapter_comments = CommentRecyclerViewAdapter(this, comments)
        commentsFilmRecyclerView.setAdapter(adapter_comments)
    }

    var offline:Boolean ?= null
    var film : Movie ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        index = intent.extras.getInt("index", 0)
        offline = intent.extras.getBoolean("mode", false)

        if(!offline!!){
            film =  Store.homeFilms[0]
            Store.homeFilms.forEach { it ->
                if (it.id == index)
                    film = it
            }
            initDetailFilmDataAPI(film!!.id)
            setUpLayout()
        }else{
            initDBOffline()
        }

        // Ask for permission to stock images
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
        }

    }

    //Add search view
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_love, menu)
        loveItem = menu.findItem(R.id.love_item)
        initDB()
        menu.findItem(R.id.love_item).setIcon(R.drawable.heart_inactive)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.love_item) {
            if (!favoriteMoviesId!!.contains(film!!.id)) {
                item.setIcon(R.drawable.heart2)
                saveMovieAndRelatedMovies(film!!)
                Toast.makeText(this, "Film ajouté à Mes Fans", LENGTH_SHORT).show()
            } else {
                item.setIcon(R.drawable.heart_inactive)
                deleteMovie(film!!)
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
                trailerVideo.seekTo(1000)
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

    fun setUpLayout(){
        glide = Glide.with(this)
        glide!!.load(Config.IMG_BASE_URL + film!!.poster_path)
                .into(filmCard)
        filmTitle.text = film!!.title
        glide!!.load(Config.IMG_BASE_URL + film!!.poster_path)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable,
                                                 transition: Transition<in Drawable>?) {
                        frameLayout.setBackground(resource)
                    }
                })


        if(! film!!.genre_ids.isEmpty()) {
            film!!.genresList = Genre.genresList.get(film!!.genre_ids[0])?.name + ""
            for (i in 1 until film!!.genre_ids.size) {
                film!!.genresList += " / " + Genre.genresList.get(film!!.genre_ids[i])?.name
            }
        }

        evaluationText.text = film!!.vote_average.toString()

        descriptionText.text = film!!.description

        filmDate.text = "(${SimpleDateFormat("yyyy").format(film!!.release_date)})"

        filmType.text = film!!.genresList

        setSupportActionBar(toolbar_detail_film)
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }
        //Set activity title
        toolbar_detail_film.title = film!!.title

        //Init trailer video
        initTrailer(trailer_video)

        //Hide Media controller when scrolling
        scrollContainer.getViewTreeObserver().addOnScrollChangedListener(
                ViewTreeObserver.OnScrollChangedListener {
                    mediaController!!.hide()
                })

        val salles = Mocker.salleList.getRandomElements_(4)
        film!!.cinemas = salles
        initSallesRecyclerView(salles)
    }

    fun initDBOffline() {
        var act = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                act.db = MovieDB.getInstance(act)
                act.movieDao = act.db!!.movieDAO()
                act.relatedMovieDao = act.db!!.relatedMoviesDAO()
                return null
            }

            override fun onPostExecute(result: Void?) {
                Store.favFilms.forEach { it ->
                    if (it.id == act.index)
                        film = it
                }
                act.setUpLayout()
                act.associateMovies()
                Log.i("bd","bd created")
            }
        }.execute()
    }

    fun associateMovies() {
        var act = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                    val associatedMovies = act.relatedMovieDao?.getRelatedMovies(film!!.id)
                    film!!.linkedMovies = associatedMovies
                    Log.i("related", film!!.linkedMovies!!.toString())
                return null
            }

            override fun onPostExecute(result: Void?) {
            //    Store.favFilms = act.fanFilms as ArrayList<Movie>
                act.initAssociatedFilmsRecyclerView(film!!.linkedMovies!!,true)
                Log.i("bd","bd created")
            }
        }.execute()
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
                    film!!.linkedMovies = relatedMovies
                    relatedMovies.forEach { it ->
                        Store.homeFilms.add(it)
                    }
                    initAssociatedFilmsRecyclerView(relatedMovies)
                }

            }

            override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?){
                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
            }
        })

        service.reviewsMovie(movieId).enqueue(object: Callback<ReviewsResponse> {
            override fun onResponse(call: Call<ReviewsResponse>, response: retrofit2.Response<ReviewsResponse>?) {
                if ((response != null) && (response.code() == 200)) {
                    val comments = response.body()!!.results
                    film!!.comments = comments
                    initCommentsRecyclerView(comments)
                }
            }
            override fun onFailure(call: Call<ReviewsResponse>?, t: Throwable?){
                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
            }
        })

    }

    // Fonctions to save in DB
    fun saveMovieAndRelatedMovies(movie: Movie){
        saveMovie(movie = movie,related = null,recursive = true)
        // Save image
        ImageManager.saveImageBitmap(this, filmCard ,movie.id.toString())
        for (movieR in movie.linkedMovies!!){
            saveImageGlide(movieR)
        }
    }

    fun saveMovie(movie: Movie, related: Movie? =null, recursive:Boolean=false) {
        var act = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                if(recursive){
                    movie.fav = true
                    act.favoriteMoviesId!!.add(movie.id)
                    val nb = act.relatedMovieDao?.nbrAssociation(movie.id)!!
                    if (nb>0){
                        act.movieDao?.setFav(movie.id,1)
                    }else{
                        act.movieDao?.insert(movie)
                    }
                    var index = 0
                    for (relatedMv in movie.linkedMovies!!){
                            saveMovie(movie = movie,related = relatedMv,recursive = false)
//                            act.saveImageGlide(relatedMv)
                            index++
                    }
                }else{
                    val nb = act.relatedMovieDao?.nbrAssociation(related!!.id)!!
                    if (! act.favoriteMoviesId!!.contains(related!!.id) && nb == 0){
                        act.movieDao?.insert(related!!)
                        saveMovieRelation(movie, related!!)
//                        ImageManager.saveAssociatedImage(act,act.associatedFilmsRecyclerView,related!!.id,movieIndex)
//
                    }
//                    getRelatedMovies(movie.id)
                }
                return null
            }


            override fun onPostExecute(result: Void?) {
            }
        }.execute()
    }

    fun deleteMovie(movie: Movie) {
        var act = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                    movie.fav = false
                    act.favoriteMoviesId!!.remove(movie.id)
                    val nb = act.relatedMovieDao?.nbrAssociation(movie.id)!!
                    act.relatedMovieDao?.deleteAllRelated(movie.id)  // delete all related
                    for (relatedMv in movie.linkedMovies!!){
                        if (!act.favoriteMoviesId!!.contains(relatedMv.id) && act.relatedMovieDao?.nbrAssociation(relatedMv.id)!! == 0){
                            act.movieDao?.delete(relatedMv)
                            ImageManager.deleteImage(relatedMv.id.toString())
                        }
                    }
                if (nb == 0){
                    act.movieDao?.delete(movie)
                    Log.i("deleted?",ImageManager.deleteImage(movie.id.toString()).toString())
                }else{
                    act.movieDao?.setFav(movie.id,0)
                }
//                getRelatedMovies(movie.id)
                return null
            }


            override fun onPostExecute(result: Void?) {
            }
        }.execute()
    }

    fun saveMovieRelation(movie: Movie, related: Movie) {
        var act = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                act.relatedMovieDao?.insert(AssotiationMovies(movie.id ,related.id))

                return null
            }


            override fun onPostExecute(result: Void?) {
            }
        }.execute()
    }

    fun initDB() {
        var act = this
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                act.db = MovieDB.getInstance(act)
                act.movieDao = act.db!!.movieDAO()
                act.relatedMovieDao = act.db!!.relatedMoviesDAO()
                act.favoriteMoviesId = act.movieDao?.getFavMoviesId() as ArrayList<Int>?
                return null
            }

            override fun onPostExecute(result: Void?) {
                Log.i("bd","bd created")
                act.loveItem!!.setEnabled(true)
                //Set fun heart icon for the first time
                if (favoriteMoviesId!!.contains(film!!.id)) {
                    act.loveItem!!.setIcon(R.drawable.heart2)
                }
            }
        }.execute()
    }

    override fun onPause() {
        super.onPause()
        if(loveItem != null) loveItem!!.setEnabled(false)
    }

    override fun onRestart() {
        super.onRestart()
//        Log.i("restart","_____")
        initDB()
    }

    fun saveImageGlide(movie: Movie){
        glide!!.asBitmap()
                .load(Config.IMG_BASE_URL + movie.poster_path)
                .into( object : SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        // Save image
                        ImageManager.saveImage(this@FilmDetailActivity, resource ,movie.id.toString())
                    }
                })
    }
}
