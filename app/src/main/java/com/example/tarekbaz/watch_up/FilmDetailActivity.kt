package com.example.tarekbaz.watch_up

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_film.*
import kotlinx.android.synthetic.main.content_home.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FilmDetailActivity : AppCompatActivity() {
    //For testing detail film
    val salleNames: List<String> = mutableListOf(
            "Cinema Paris Salle 0012", "Larousse Cinema",  "Cinema des Rois -Marseille-"
    )

    var str1 = "22:03"
    var str2 = "22:30"
    var formatter: DateFormat = SimpleDateFormat("HH:mm")
    var date1 = formatter.parse(str1)
    var date2 = formatter.parse(str2)
    val filmsTimes: List<Date> = mutableListOf(
            date1, date2, date1
    )

    //Associated Films
    val filmNames: List<String> = mutableListOf(
            "La Belle et La Bète", "Hunger Game", "Drone"
    )

    val imageFilmsUrls: List<Int> = mutableListOf(
            R.drawable.film4,R.drawable.film5,R.drawable.serie1
    )

    private fun initSallesRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sallesRecyclerView.setLayoutManager(layoutManager)
        val adapter_salles = SalleRecyclerViewAdapter(this, salleNames , filmsTimes)
        sallesRecyclerView.setAdapter(adapter_salles)
    }

    private fun initAssociatedFilmsRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        associatedFilmsRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = HomeRecyclerViewAdapter(this, filmNames , imageFilmsUrls)
        associatedFilmsRecyclerView.setAdapter(adapter_films)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)
        this.initSallesRecyclerView()
        this.initAssociatedFilmsRecyclerView()
    }
}
