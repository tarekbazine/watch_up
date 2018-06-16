package com.example.tarekbaz.watch_up

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import com.example.tarekbaz.watch_up.Models.Genre
import com.example.tarekbaz.watch_up.Models.Store
import kotlinx.android.synthetic.main.activity_settings.*
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import android.os.Build
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.tarekbaz.watch_up.Models.Movie
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

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
            if (Store.preferedGenres.contains(cb.id)) cb.isChecked = true
            cb.setOnCheckedChangeListener { _cb, b ->
                if (b) {
                    Store.preferedGenres.add(_cb.id)
//                    genres.add(_cb.id.toString())
//                    editor.putStringSet(Genre.KEY, genres)
                } else {
                    Store.preferedGenres.remove(_cb.id)
//                    genres.remove(_cb.id.toString())
//                    editor.putStringSet(Genre.KEY, genres)
                }
//                editor.commit()
//                Log.i("myLogiii", genres.toString())
//                Log.i("myLogiii2", prefGenres.getStringSet(Genre.KEY, HashSet<String>()).toString())
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
            NewMoviesNotification.create(applicationContext)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()

        val prefGenres = getSharedPreferences(Genre.KEY, Context.MODE_PRIVATE)
        val genres = prefGenres.getStringSet(Genre.KEY, HashSet<String>())
        val editor = prefGenres.edit()

        //todo
        editor.putStringSet(Genre.KEY, null)
        editor.commit()

        Store.preferedGenres.forEach { it ->
            genres.add(it.toString())
        }
        editor.putStringSet(Genre.KEY, genres)
        editor.commit()
    }
}
