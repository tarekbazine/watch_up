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


class FilmDetailActivity : AppCompatActivity() {
    //For testing detail film
    val film_title = "La Belle et La Bète"
    val salleNames: List<String> = mutableListOf(
            "Cinema Paris Salle 0012", "Larousse Cinema", "Cinema des Rois -Marseille-"
    )

    var str1 = "22:03"
    var str2 = "22:30"
    var formatter: DateFormat = SimpleDateFormat("HH:mm")
    var date1 = formatter.parse(str1)
    var date2 = formatter.parse(str2)
    var is_fan = true

    //Video attributes
    var trailer_video = R.raw.trailer1
    private var mediaController: MediaController? = null
    private var positionVideo:Int = 0

    val filmsTimes: List<Date> = mutableListOf(
            date1, date2, date1
    )

    //Associated Films
    val filmNames: List<String> = mutableListOf(
            "La Belle et La Bète", "Hunger Game", "Drone"
    )

    val imageFilmsUrls: List<Int> = mutableListOf(
            R.drawable.film4, R.drawable.film5, R.drawable.serie1
    )

    //Comments
    val userNames: List<String> = mutableListOf(
            "Cinema Paris Salle 0012", "Larousse Cinema", "Cinema des Rois -Marseille-"
    )

    val commentDates: List<String> = mutableListOf(
            "7/7 de 8:00 à 23:00", "24/24 sauf samedi de 8:00 à 23:00", "Toujours 10:00 à 23:00", "de 8:00 à 20:00 sauf lundi"
    )



    val comments: List<String> = mutableListOf(
            "Le nombre de points de fonction non ajusté, ou brut :" +
                    "le nombre de fonctionnalités fournies à l'utilisateur par" +
                    "l'application. ",
            "Peut représenter le résultat d’un traitement (créer, modifier et supprimer) autre qu’un\n" +
                    "simple traitement d’extraction de données.",
            "Live from the sea", "Directly from desert"
    )


    //Init adapters
    private fun initSallesRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sallesRecyclerView.setLayoutManager(layoutManager)
        val adapter_salles = SalleRecyclerViewAdapter(this, salleNames, filmsTimes)
        sallesRecyclerView.setAdapter(adapter_salles)
    }

    private fun initAssociatedFilmsRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        associatedFilmsRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = HomeRecyclerViewAdapter(this, filmNames, imageFilmsUrls)
        associatedFilmsRecyclerView.setAdapter(adapter_films)
    }

    private fun initCommentsRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        commentsFilmRecyclerView.setLayoutManager(layoutManager)
        val adapter_comments = CommentRecyclerViewAdapter(this, userNames , comments, commentDates)
        commentsFilmRecyclerView.setAdapter(adapter_comments)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        setSupportActionBar(toolbar_detail_film)
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }
        //Set activity title
        toolbar_detail_film.title = this.film_title

        //Init trailer video
        initTrailer(trailer_video)

        //Hide Media controller when scrolling
        scrollContainer.getViewTreeObserver().addOnScrollChangedListener(
                ViewTreeObserver.OnScrollChangedListener { mediaController!!.hide()
                })

        this.initSallesRecyclerView()
        this.initAssociatedFilmsRecyclerView()
        initCommentsRecyclerView()
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
                Toast.makeText(this, "Film ajouté à Mes Fans", LENGTH_SHORT).show()
            } else {
                this.is_fan = true
                item.setIcon(R.drawable.heart_inactive)
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
            if(this.positionVideo == 0){
                trailerVideo.seekTo(10000)
            }else{
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
        trailerVideo.setPlayPauseListener (object : CustomVideoView.PlayPauseListener {
            override fun onPlay() {
                trailerImage.visibility = View.GONE
            }
            override fun onPause() {
                trailerImage.visibility = View.VISIBLE
                positionVideo = trailerVideo.getCurrentPosition()
            }
            override fun onTouch() {
                if (! trailerVideo.isPlaying){
                    trailerVideo.start()
                }
            }
        })
    }


    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    public override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        if (trailerVideo.isPlaying){
            savedInstanceState!!.putBoolean("VideoIsPlaying", true)
            trailerVideo.pause()
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
            trailerVideo.start()
        }
    }
}
