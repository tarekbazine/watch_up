package com.example.tarekbaz.watch_up

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.tarekbaz.watch_up.Models.Comment
import com.example.tarekbaz.watch_up.Models.Mocker
import com.example.tarekbaz.watch_up.Models.Season
import com.example.tarekbaz.watch_up.Models.Serie
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail_serie.*

class SerieDetailActivity : AppCompatActivity() {

    var is_fan = false

    private fun initSeasonsRecyclerView(seasons : List<Season>,index : Int) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        seasonsRecyclerView.setLayoutManager(layoutManager)
        val adapter_seasons= SeasonRecyclerViewAdapter(this,seasons , index)
        seasonsRecyclerView.setAdapter(adapter_seasons)
    }

    private fun initAssociatedSeriesRecyclerView(assSerie : List<Serie>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        associatedSeriesRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = HomeSerieRecyclerViewAdapter(this, assSerie)
        associatedSeriesRecyclerView.setAdapter(adapter_films)
    }

    private fun initCommentsRecyclerView(comments: List<Comment>) {
        val layoutManager = LinearLayoutManager(this)
        commentsSerieRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = CommentRecyclerViewAdapter(this, comments)
        commentsSerieRecyclerView.setAdapter(adapter_films)
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

    var serie:Serie  = Mocker.serieList[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_serie)

        val index = intent.extras.getInt("index",0)

        Mocker.serieList.forEach { it ->
            if (it.id == index)
                serie = it
        }
        val comments = serie.comments
        val assSeries = serie.linkedSeries
        val seasons = serie.seasons

        Mocker.favSerieList.forEach { it ->
            if(it.id == serie.id)
                is_fan = true
        }

        frameLayout.setBackgroundResource(serie.image)
        filmCard.setImageResource(serie.image)
        serieTitle.text = serie.title
        descriptionText.text = serie.discription

        this.initSeasonsRecyclerView(seasons, index)
        this.initAssociatedSeriesRecyclerView(assSeries)
        this.initCommentsRecyclerView(comments)

        setSupportActionBar(toolbar_detail_serie)
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }
        //Set activity title
        toolbar_detail_serie.title = serie.title

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.love_item) {
            if (this.is_fan) {
                this.is_fan = false
                item.setIcon(R.drawable.heart2)
                Mocker.favSerieList.add(serie)
                Toast.makeText(this, "Série ajoutée à Mes Fans", Toast.LENGTH_SHORT).show()
            } else {
                this.is_fan = true
                item.setIcon(R.drawable.heart_inactive)
                Mocker.favSerieList.remove(serie)
                Toast.makeText(this, "Série enlevée de Mes Fans", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}