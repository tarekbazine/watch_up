package com.example.tarekbaz.watch_up

import android.content.Context
import android.os.Bundle
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

    var dialog: AlertDialog? = null

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

        initDataAPI()
        dialog = showDialog()
//        Timer().schedule(object : TimerTask() {
//            override fun run() {
//                hideDialog(dialog)
//            }
//        }, 5000)
    }


    fun initDataAPI(){

        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val service = retrofit.create<Service>(Service::class.java!!)

        service.getHomeMovies().enqueue(object: Callback<MoviesResponse> {

            override fun onResponse(call: Call<MoviesResponse>, response: retrofit2.Response<MoviesResponse>?) {
                if ((response != null) && (response.code() == 200)) {

                    val movies = response.body()!!.results

                    Store.homeFilms = ArrayList(movies)

                    // Save in database
//                     saveMovie(movie = movies.get(1))
                    //Save relation
//                    saveMovieRelation(dbAllMovies!![0] , dbAllMovies!![1])
                    // get related movies
//                    Log.i("related", dbAllMovies!![0].id.toString())
//                    getRelatedMovies(dbAllMovies!![0].id)



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
                    initFilmRecyclerView(movies)
                    hideDialog(dialog)
            }

            }

            override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?){
                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
                Timer().schedule(object : TimerTask() {
                        override fun run() {
                            hideDialog(dialog)
                            showFailDialog()
                        }
                    }, 5000)
                }
        })

        service.getTodayAiringSeries().enqueue(object: Callback<SeriesResponse> {
            override fun onResponse(call: Call<SeriesResponse>, response: retrofit2.Response<SeriesResponse>?) {
                if ((response != null) && (response.code() == 200)) {
                    val series = response.body()!!.results
                    Store.homeSeries = ArrayList(series)
                    initSerieRecyclerView(series)
                }
            }
            override fun onFailure(call: Call<SeriesResponse>?, t: Throwable?){
                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
            }
        })

    }

    // this function shows a dialog_progress dialogue
    fun showDialog(): AlertDialog {
        //Loading spinner
        val builder = AlertDialog.Builder(this,R.style.Loading_Style)
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE  ) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_progress,null)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
        return dialog
    }

    fun hideDialog(dialog: AlertDialog?){
        //Loading spinner
        dialog!!.dismiss()
    }

    fun showFailDialog(): AlertDialog {
        //Loading spinner
        val builder = AlertDialog.Builder(this,R.style.Loading_Style)
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE  ) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_failed,null)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
        return dialog
    }
}

