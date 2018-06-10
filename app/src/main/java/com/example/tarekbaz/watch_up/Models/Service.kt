package com.example.tarekbaz.watch_up.Models

import com.example.tarekbaz.watch_up.Config
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.MoviesResponse
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.SerieResponse
import retrofit2.Call
import retrofit2.http.GET

//get new movies
//https://api.themoviedb.org/3/ movie/upcoming?api_key=88866508ff0c05eb70a2cad3a137afa1&language=en-US&page=1

//get the images
//https://image.tmdb.org/t/p/w185       "concate !!"     /4oD6VEccFkorEBTEDXtpLAaz0Rl.jpg
interface Service {

    @GET("movie/upcoming?api_key=${Config.API_KEY}&language=en-US&page=1")
    fun getHomeMovies(): Call<MoviesResponse>

    @GET("tv/airing_today?api_key=${Config.API_KEY}&language=en-US&page=1")
    fun getTodayAiringSeries(): Call<SerieResponse>
}