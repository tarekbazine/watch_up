package com.example.tarekbaz.watch_up

import android.content.Context
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.widget.Toast
import com.example.tarekbaz.watch_up.Adapters.HomeMovieRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.HomeSerieRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Models.*
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.MoviesResponse
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.SeriesResponse
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class HomeActivity : BaseActivity() {


    private fun initFilmRecyclerView(films : List<Movie>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        home_film_slider.setLayoutManager(layoutManager)
        val adapter_films = HomeMovieRecyclerViewAdapter(this, films)
        home_film_slider.setAdapter(adapter_films)
    }

    private fun initSerieRecyclerView(series : List<Serie>) {
        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        home_serie_slider.setLayoutManager(layoutManager2)
        val adapter_series = HomeSerieRecyclerViewAdapter(this, series)
        home_serie_slider.setAdapter(adapter_series)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        //Add drawer button
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
//        initDataAPI()
        val movies =  Store.homeFilms
        initFilmRecyclerView(movies)
        val series = Store.homeSeries
        initSerieRecyclerView(series)
    }
}

