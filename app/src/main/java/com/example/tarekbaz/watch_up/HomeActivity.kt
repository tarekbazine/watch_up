package com.example.tarekbaz.watch_up

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.tarekbaz.watch_up.API.MovieService
import com.example.tarekbaz.watch_up.API.Responses.ListPaginatedResponse
import com.example.tarekbaz.watch_up.API.SerieService
import com.example.tarekbaz.watch_up.Adapters.HomeMovieRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Adapters.HomeSerieRecyclerViewAdapter
import com.example.tarekbaz.watch_up.Models.Movie
import com.example.tarekbaz.watch_up.Models.Serie
import com.example.tarekbaz.watch_up.Models.Store
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class HomeActivity : BaseActivity() {

    var alreadyRequestedPagesSerie = 1
    var alreadyRequestedPagesMovie = 1
    var adapter_films : HomeMovieRecyclerViewAdapter? = null
    var adapter_series : HomeSerieRecyclerViewAdapter? = null

    private fun initFilmRecyclerView(films : List<Movie>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        home_film_slider.setLayoutManager(layoutManager)
        adapter_films = HomeMovieRecyclerViewAdapter(this, films)
        home_film_slider.setAdapter(adapter_films)

        home_film_slider.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var pastVisiblesItems: Int = 0
            var visibleItemCount: Int = 0
            var totalItemCount: Int = 0

            override fun onScrolled(recyclerView: RecyclerView?,
                                    dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = layoutManager.childCount
                totalItemCount = layoutManager.itemCount
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                val availablePages = (totalItemCount/Config.ITEMS_PER_PAGE)

                if (visibleItemCount + pastVisiblesItems >= (totalItemCount - Config.NEXT_PAGE_LIMIT)
                && alreadyRequestedPagesMovie <= availablePages){
                    alreadyRequestedPagesMovie = availablePages + 1
//                    Log.i("HomeLog","next p "+availablePages+" "+visibleItemCount +" " +pastVisiblesItems+" "+totalItemCount)
                    getMovie(availablePages + 1)
                }
            }
        })
    }

    private fun initSerieRecyclerView(series : List<Serie>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        home_serie_slider.setLayoutManager(layoutManager)
        adapter_series = HomeSerieRecyclerViewAdapter(this, series)
        home_serie_slider.setAdapter(adapter_series)

        home_serie_slider.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var pastVisiblesItems: Int = 0
            var visibleItemCount: Int = 0
            var totalItemCount: Int = 0

            override fun onScrolled(recyclerView: RecyclerView?,
                                    dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = layoutManager.childCount
                totalItemCount = layoutManager.itemCount
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                val availablePages = (totalItemCount/Config.ITEMS_PER_PAGE)

                if (visibleItemCount + pastVisiblesItems >= (totalItemCount - Config.NEXT_PAGE_LIMIT)
                        && alreadyRequestedPagesSerie <= availablePages){
                    alreadyRequestedPagesSerie = availablePages + 1
//                    Log.i("HomeLog serie","next p "+availablePages+" "+visibleItemCount +" " +pastVisiblesItems+" "+totalItemCount)
                    getSerie(availablePages + 1)
                }
            }
        })
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

        val movies =  Store.homeFilms
        initFilmRecyclerView(movies)
        val series = Store.homeSeries
        initSerieRecyclerView(series)
    }

    private fun getMovie(page : Int){
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val service = retrofit.create<MovieService>(MovieService::class.java!!)

        service.getHomeMovies(page).enqueue(object: Callback<ListPaginatedResponse<Movie>> {

            override fun onResponse(call: Call<ListPaginatedResponse<Movie>>, response: retrofit2.Response<ListPaginatedResponse<Movie>>?) {
                if ((response != null) && (response.code() == 200)) {

                    val movies = response.body()!!.results

                    Store.homeFilms.addAll(ArrayList(movies))

                    adapter_films!!.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<ListPaginatedResponse<Movie>>?, t: Throwable?){
                Toast.makeText(baseContext, "Echec p: "+page, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getSerie(page : Int){
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val service = retrofit.create<SerieService>(SerieService::class.java!!)

        service.getTodayAiringSeries(page).enqueue(object: Callback<ListPaginatedResponse<Serie>> {
            override fun onResponse(call: Call<ListPaginatedResponse<Serie>>, response: retrofit2.Response<ListPaginatedResponse<Serie>>?) {
                if ((response != null) && (response.code() == 200)) {
                    val series = response.body()!!.results

                    Store.homeSeries.addAll(ArrayList(series))

                    adapter_series!!.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<ListPaginatedResponse<Serie>>?, t: Throwable?){
                Toast.makeText(baseContext, "Echec page S "+ page, Toast.LENGTH_LONG).show()
            }
        })

    }

}

