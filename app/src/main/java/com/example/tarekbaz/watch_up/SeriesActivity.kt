package com.example.tarekbaz.watch_up

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu

import kotlinx.android.synthetic.main.activity_series.*
import kotlinx.android.synthetic.main.drawer_activity.*
import android.text.TextUtils



class SeriesActivity  : BaseActivity()  {

    var adapter_series : SeriesRecyclerViewAdapter? = null

    //TODO USE Models
    val filmNames: List<String> = mutableListOf(
            "La Belle et La Bète", "Hunger Game", "Drone", "Hunger Game 2"
    )

    val imageFilmsUrls: List<Int> = mutableListOf(
            R.drawable.film4, R.drawable.film5, R.drawable.serie1, R.drawable.film5
    )

    val filmDirectors: List<String> = mutableListOf(
            "Le throne de Fer", "La Casa de Papel", "Breaking Bad", "LaCasa de Papel"
    )

    //Add search view
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    adapter_series!!.filter("")
                } else {
                    adapter_series!!.filter(newText)
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }


    private fun initSerieCardsRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        seriesRecyclerView.setLayoutManager(layoutManager)
        val adapter_series = SeriesRecyclerViewAdapter(this, filmNames , imageFilmsUrls, filmDirectors)
        seriesRecyclerView.setAdapter(adapter_series)
        this.adapter_series = adapter_series
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series)
        setSupportActionBar(toolbar_series)
        //Add drawer button
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar_series, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        initSerieCardsRecyclerView()
    }

}
