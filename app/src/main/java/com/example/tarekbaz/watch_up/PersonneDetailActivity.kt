package com.example.tarekbaz.watch_up

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.tarekbaz.watch_up.Models.Mocker
import com.example.tarekbaz.watch_up.Models.Movie
import com.example.tarekbaz.watch_up.Models.Person
import kotlinx.android.synthetic.main.activity_detail_personne.*
import android.view.MenuItem


class PersonneDetailActivity : AppCompatActivity() {

    val nationality = "Britanique"
    val works = 19

    private fun initfilmographyRecyclerView(movies: List<Movie>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        filmographieRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = HomeMovieRecyclerViewAdapter(this, movies)
        filmographieRecyclerView.setAdapter(adapter_films)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_personne)


        val index = intent.extras.getInt("index", 0)
        val isActor = intent.extras.getBoolean("isActor", true)

        val person: Person
        if (isActor)
            person = Mocker.actorList[index]
        else
            person = Mocker.directorList[index]

        val movies = person.movies

        setSupportActionBar(toolbar_detail_personne)
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }

        //Set title
        toolbar_detail_personne.title = person.name

        personneCard.setImageResource(person.picture)
        personne_name.text = person.name
        birthdayText.text = person.birthDay
        nationalityText.text = nationality
        works_numText.text = works.toString()
        bibliographieContent.text = person.bibliography

        initfilmographyRecyclerView(movies)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
