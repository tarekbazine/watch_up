package com.example.tarekbaz.watch_up

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import com.example.tarekbaz.watch_up.Models.Genre
import com.example.tarekbaz.watch_up.Models.Store
import kotlinx.android.synthetic.main.activity_settings.*
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import android.widget.Toast
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.MoviesResponse
import com.example.tarekbaz.watch_up.Models.Service
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.graphics.Bitmap
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.tarekbaz.watch_up.Models.Movie
import java.text.SimpleDateFormat


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        ///***///
        //todo for API 26+


        ///***///

        setSupportActionBar(toolbar_settings)
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }


        val checkBoxList = ArrayList<CheckBox>()

        for (i in 0 until Genre.movieGenres.size) {
            val cb = CheckBox(this)
            cb.setTextColor(Color.WHITE)
            cb.text = Genre.movieGenres[i].name
            cb.id = Genre.movieGenres[i].id
            if (Store.preferedGenres.get(cb.id) != null) cb.isChecked = true
            cb.setOnCheckedChangeListener { _cb, b ->
                if (b) {
                    Store.preferedGenres.put(_cb.id, Genre(_cb.id, _cb.text.toString()))
                } else {
                    Store.preferedGenres.remove(_cb.id)
                }
            }
            checkBoxList.add(cb)

            if (i > (Genre.movieGenres.size / 2))
                checkBoxContainer2.addView(cb)
            else
                checkBoxContainer1.addView(cb)

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            api()

            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    fun api() {//todo check genre exist !!! at least one
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val service = retrofit.create<Service>(Service::class.java!!)

//        Log.i("myLog",""+Store.preferedGenres.size)
//        Log.i("myLog",Store.preferedGenres.toString())
//        Log.i("myLog",Store.preferedGenres[0]?.toString())


        val keys = ArrayList(Store.preferedGenres.keys)

        var genresList = keys[0].toString()
        for (i in 1 until Store.preferedGenres.size){
            genresList += "|" + keys[i]
        }

        Log.i("myLog",genresList)

        service.latestMovies("2019-06-14", "2019-06-20", genresList)
                .enqueue(object : Callback<MoviesResponse> {
                    override fun onResponse(call: Call<MoviesResponse>, response: retrofit2.Response<MoviesResponse>?) {
                        if ((response != null) && (response.code() == 200)) {
                            val latestMovie = response.body()!!.results[0]

                            Store.homeFilms.add(latestMovie)

                            initNotificatioData(latestMovie)

                        }

                    }

                    override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?) {
                        Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
                    }
                })

    }

    fun initNotificatioData(film : Movie){
        Glide.with(applicationContext)
                .asBitmap()
                .load(Config.IMG_BASE_URL+film.poster_path)
                .into(object : SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        createNotification(resource,film)
                    }
                })
    }

    private fun createNotification(bitmap : Bitmap,film : Movie) {//185 278
        // Create an explicit intent for an Activity in your app
        val intent = Intent(this, FilmDetailActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("index", 0)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

//        Store.homeFilms.add(films.get(position))

        val CHANNEL_ID = "CHANNEL_WU_01"
        val notificationId = 1
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.calendar_time_icon)
                .setLargeIcon(bitmap)
                .setContentTitle(film.title)
                .setContentText("Premium show: ${SimpleDateFormat("yyyy").format(film.release_date)}")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)


        //run
        val notificationManager = NotificationManagerCompat.from(this)
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, mBuilder.build())

    }
}
