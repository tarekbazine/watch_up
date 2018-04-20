package com.example.tarekbaz.watch_up

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_personne.*
import android.view.MenuItem


class PersonneDetailActivity : AppCompatActivity() {
    //TODO Replace with object
    val name = "Luke Evans"
    val birthday = "22 Mars 1991 (29 ans)"
    val nationality = "Britanique"
    val works = 19
    val biography = " L’application interactive que nous proposons est destinée à un large public incluant différentes catégories d’utilisateurs à commencer par des personnes à la recherche d’offres de voyages d’une part, et d’autre part des agences de voyages qui veulent poster leurs offres de voyages ou encore des particuliers qui souhaitent louer leurs biens immobiliers ou véhicules pour les vacances"


    //For testing filmography
    val filmNames: List<String> = mutableListOf(
            "La Belle et La Bète", "Hunger Game", "Drone"
    )

    val imageFilmsUrls: List<Int> = mutableListOf(
            R.drawable.film4,R.drawable.film5,R.drawable.serie1
    )

    private fun initfilmographyRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        filmographieRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = HomeRecyclerViewAdapter(this, filmNames , imageFilmsUrls)
        filmographieRecyclerView.setAdapter(adapter_films)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_personne)

        setSupportActionBar(toolbar_detail_personne)
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }

        //Set title
        toolbar_detail_personne.title = name

        personneCard.setImageResource(R.drawable.person1)
        birthdayText.text = birthday
        nationalityText.text = nationality
        works_numText.text = works.toString()
        bibliographieContent.text = biography
        this.initfilmographyRecyclerView()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
