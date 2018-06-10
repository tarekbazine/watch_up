package com.example.tarekbaz.watch_up

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.tarekbaz.watch_up.Adapters.HomeMovieRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.HomeSerieRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Models.Mocker
import com.example.tarekbaz.watch_up.Models.Movie
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.MoviesResponse
import com.example.tarekbaz.watch_up.Models.Service
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeActivity : BaseActivity() {

    val series = Mocker.serieList

    private fun initRecyclerView(films : List<Movie>) {
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

        getHomeMoviesAPI()

    }


    fun getHomeMoviesAPI(){

        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        Log.i("watchuplog", "cool" )

        val service = retrofit.create<Service>(Service::class.java!!)

        // call the method we defined in our interface, and handle the result
        service.getHomeMovies().enqueue(object: Callback<MoviesResponse> {

            override fun onResponse(call: Call<MoviesResponse>, response: retrofit2.Response<MoviesResponse>?) {
                if ((response != null) && (response.code() == 200)) {

                    var movies = response.body()!!.results
//
//
//                    Log.i("dd", ""+todos!![0].title + ""+ todos!![0].completed )
//                    Log.i("dd", ""+todos!![2].title + ""+ todos!![2].completed )
//                    Log.i("dd", ""+todos.size )
////                    txtTitle.setText(post!!.title)
////                    txtBody.setText(post!!.body)
//                    Toast.makeText(baseContext, "Succès", Toast.LENGTH_LONG).show()
//
//                    initRecyclerView(todos)


                    // init RecyclerViews
                    initRecyclerView(movies)

                    Log.i("watchuplog", "cool1" )
                    Log.i("watchuplog", "" + movies[0].toString() )
                    Log.i("watchuplog", "" + movies[0].title )
                    Log.i("watchuplog", " "+response.body().toString().length )
                }

            }

            override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?){
//                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
                Log.i("watchuplog", "error")
            }
        })


    }

}
