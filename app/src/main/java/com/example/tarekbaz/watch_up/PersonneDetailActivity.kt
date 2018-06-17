package com.example.tarekbaz.watch_up

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail_personne.*
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tarekbaz.watch_up.Adapters.HomeMovieRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Models.*
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PersonneDetailActivity : AppCompatActivity() {
    var personne: Person? = null
    var index: Int? = null


    private fun initfilmographyRecyclerView(movies: List<Movie>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        filmographieRecyclerView.setLayoutManager(layoutManager)
        val adapter_films = HomeMovieRecyclerViewAdapter(this, movies)
        filmographieRecyclerView.setAdapter(adapter_films)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_personne)

        index = intent.extras.getInt("index", 0)


        setSupportActionBar(toolbar_detail_personne)
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        }
        personne = Store.acteurs[this.index!!]

        if (personne!!.biography == null) {
            getActorDetailAPI()
        } else {
            setUpLayout()
        }
        // maj interface


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    fun getActorDetailAPI() {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val service = retrofit.create<Service>(Service::class.java)
        service.getPersonDetail(personne!!.id).enqueue(object : Callback<Person> {

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<Person>, response: retrofit2.Response<Person>?) {
                if ((response != null) && (response.code() == 200)) {

                    // Maj person
                    val data = response.body()!!
                    if (data.birthday != null) personne!!.birthday = data.birthday
                    personne!!.biography = data.biography
                    personne!!.gender = data.gender
                    if (data.place_of_birth != null) personne!!.place_of_birth = data.place_of_birth

                    personne!!.known_for.forEach { it ->
                        Store.homeFilms.add(it)
                    }
                    setUpLayout()

                    Log.i("reponse", " " + personne!!.biography)
                }
            }

            override fun onFailure(call: Call<Person>?, t: Throwable?) {
                Toast.makeText(baseContext, "Erreur de connexion", Toast.LENGTH_LONG).show()
            }
        })
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun setUpLayout() {
        //Set title
        toolbar_detail_personne.title = personne!!.name
        //image
        if (null != personne!!.profile_path)
            Glide.with(this@PersonneDetailActivity)
                    .load(Config.IMG_BASE_URL + personne!!.profile_path)
                    .into(personneCard)


        personne_name.text = personne!!.name
        val format = SimpleDateFormat("dd-MM-yyy")
        if (personne!!.birthday != null) birthdayText.text = format.format(personne!!.birthday)
        if (personne!!.place_of_birth != null) nationalityText.text = (personne!!.place_of_birth).split(",").last()
        works_numText.text = personne!!.known_for.size.toString()
        bibliographieContent.text = personne!!.biography
        initfilmographyRecyclerView(personne!!.known_for)
    }

}
