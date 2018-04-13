package com.example.tarekbaz.watch_up

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_home.*
import android.content.Intent


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //TODO USE Models
    val filmNames: List<String> = mutableListOf(
            "La Belle et La Bète", "Hunger Game", "Drone" , "Hunger Game 2"
    )

    val imageFilmsUrls: List<Int> = mutableListOf(
            R.drawable.film4,R.drawable.film5,R.drawable.serie1,R.drawable.film5
    )

    val serieNames: List<String> = mutableListOf(
            "Le throne de Fer", "LaCasa de Papel", "Breaking Bad" , "LaCasa de Papel"
    )

    val imageSerieUrls: List<Int> = mutableListOf(
            R.drawable.film1,R.drawable.serie2,R.drawable.film3,R.drawable.serie2
    )



    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        home_film_slider.setLayoutManager(layoutManager)
        val adapter_films = HomeRecyclerViewAdapter(this, filmNames , imageFilmsUrls)
        home_film_slider.setAdapter(adapter_films)

        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        home_serie_slider.setLayoutManager(layoutManager2)
        val adapter_series = HomeRecyclerViewAdapter(this, serieNames , imageSerieUrls)
        home_serie_slider.setAdapter(adapter_series)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        //init RecyclerViews
        this.initRecyclerView()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
            }
            R.id.nav_cinema -> {
                val intent = Intent(this, CinemaActivity::class.java)
                // To pass any data to next activity
//                intent.putExtra("keyIdentifier", value)
                // start your next activity
                startActivity(intent)
            }
            R.id.nav_series -> {
            //TODO change it
                val intent = Intent(this, FilmDetailActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_personnes -> {

            }
            R.id.nav_fan -> {

            }
            R.id.nav_evaluations -> {
                val intent = Intent(this, CommentEvaluationActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
