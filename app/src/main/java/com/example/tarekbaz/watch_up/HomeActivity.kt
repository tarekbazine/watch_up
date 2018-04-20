package com.example.tarekbaz.watch_up

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import com.example.tarekbaz.watch_up.Models.Mocker
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_activity.*


class HomeActivity : BaseActivity() {

    val films = Mocker.movieList

    val series = Mocker.serieList

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        home_film_slider.setLayoutManager(layoutManager)
        val adapter_films = HomeMovieRecyclerViewAdapter(this, films)
        home_film_slider.setAdapter(adapter_films)

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


        // init RecyclerViews
        this.initRecyclerView()
    }

}
