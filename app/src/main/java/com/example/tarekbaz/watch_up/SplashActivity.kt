package com.example.tarekbaz.watch_up

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.tarekbaz.watch_up.Models.Genre
import com.example.tarekbaz.watch_up.API.Responses.ListPaginatedResponse
import com.example.tarekbaz.watch_up.API.Responses.SeriesResponse
import com.example.tarekbaz.watch_up.API.Service
import com.example.tarekbaz.watch_up.Models.Movie
import com.example.tarekbaz.watch_up.Models.Store
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class SplashActivity : AppCompatActivity() {
    var initNbr = 0
    var echec:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initDataAPI()

    }

    fun initDataAPI(){

        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val service = retrofit.create<Service>(Service::class.java!!)

        service.getHomeMovies(1).enqueue(object: Callback<ListPaginatedResponse<Movie>> {

            override fun onResponse(call: Call<ListPaginatedResponse<Movie>>, response: retrofit2.Response<ListPaginatedResponse<Movie>>?) {
                if ((response != null) && (response.code() == 200)) {

                    val movies = response.body()!!.results

                    Store.homeFilms = ArrayList(movies)

                    //Start Main
                    if ((response.code() == 200) && initNbr == 1 ){
                        Genre.initPreferredGenres(baseContext)
                        NewMoviesNotification.startAlarmService(applicationContext)

                        val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                        startActivity(intent, null)
                        Log.i("2","1")
                        finish()
                    }else if(response.code() == 200) {
                        Log.i("2","2")
                        initNbr++
                    }else if (!echec){
                        Log.i("2","3")
                        echec
                        loadDialog()
                    }

                }

            }

            override fun onFailure(call: Call<ListPaginatedResponse<Movie>>?, t: Throwable?){
                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        if (!echec){
                            Log.i("2","4")
                            echec
                            loadDialog()
                        }
                    }
                }, 5000)
            }
        })

        service.getTodayAiringSeries(1).enqueue(object: Callback<SeriesResponse> {
            override fun onResponse(call: Call<SeriesResponse>, response: retrofit2.Response<SeriesResponse>?) {
                if ((response != null) && (response.code() == 200)) {
                    val series = response.body()!!.results
                    Store.homeSeries = ArrayList(series)
                    //Start Main
                    if ((response.code() == 200) && initNbr == 1 ){
                        Genre.initPreferredGenres(baseContext)
                        NewMoviesNotification.startAlarmService(applicationContext)

                        val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                        startActivity(intent, null)
                        Log.i("1","1")
                        finish()
                    }else if(response.code() == 200) {
                        Log.i("1","2")
                        initNbr++
                    }else if (!echec){
                        Log.i("1","3")
                        echec
                        loadDialog()
                    }
                }
            }
            override fun onFailure(call: Call<SeriesResponse>?, t: Throwable?){
                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        if (!echec){
                            echec
                            Log.i("1","4")
                            loadDialog()
                        }
                    }
                }, 5000)
            }
        })

    }

    fun loadDialog(){
        runOnUiThread {
            loader.visibility = View.GONE
            dialog_layout.visibility = View.VISIBLE
            failed_refresh.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    //restart the app
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
            })
            failed_fav.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    val intent = Intent(this@SplashActivity, FanActivity::class.java)
                    startActivity(intent, null)
                }
            })
        }
    }
}
