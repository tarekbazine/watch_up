package com.example.tarekbaz.watch_up.API

import com.example.tarekbaz.watch_up.API.Responses.CreditsResponse
import com.example.tarekbaz.watch_up.API.Responses.ListPaginatedResponse
import com.example.tarekbaz.watch_up.Config
import com.example.tarekbaz.watch_up.Models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    /***Movie***/
    @GET("movie/upcoming?api_key=${Config.API_KEY}&language=en-US")
    fun getHomeMovies(@Query("page") page: Int): Call<ListPaginatedResponse<Movie>>

    @GET("movie/{movie_id}?api_key=${Config.API_KEY}&language=en-US")
    fun movieDetails(@Path("movie_id") id: Int): Call<Movie>

    @GET("movie/{movie_id}/similar?api_key=${Config.API_KEY}&language=en-US&page=1")
    fun relatedMovies(@Path("movie_id") id: Int): Call<ListPaginatedResponse<Movie>>

    @GET("movie/{movie_id}/reviews?api_key=${Config.API_KEY}&language=en-US&page=1")
    fun reviewsMovie(@Path("movie_id") id: Int): Call<ListPaginatedResponse<Comment>>

    @GET("movie/{movie_id}/credits?api_key=${Config.API_KEY}&language=en-US")
    fun creditsMovie(@Path("movie_id") id: Int): Call<CreditsResponse>

    @GET("search/movie?api_key=${Config.API_KEY}&language=en-US&page=1")
    fun searchMovies(@Query("query") query: String): Call<ListPaginatedResponse<Movie>>

    @GET("discover/movie?sort_by=release_date.desc&api_key=${Config.API_KEY}&language=en-US&page=1")
    fun latestMovies(//getLatestMovies
            @Query("release_date.gte") date_inf: String,
            @Query("release_date.lte") date_sup: String,
            @Query("with_genres") genre_ids: String
    ): Call<ListPaginatedResponse<Movie>>

    /***Serie***/
    @GET("tv/airing_today?api_key=${Config.API_KEY}&language=en-US")
    fun getTodayAiringSeries(@Query("page") page: Int): Call<ListPaginatedResponse<Serie>>

    @GET("tv/{tv_id}/similar?api_key=${Config.API_KEY}&language=en-US&page=1")
    fun relatedSeries(@Path("tv_id") id: Int): Call<ListPaginatedResponse<Serie>>

    @GET("tv/{tv_id}?api_key=${Config.API_KEY}&language=en-US")
    fun serieDetails(@Path("tv_id") id: Int): Call<Serie>

    @GET("search/tv?api_key=${Config.API_KEY}&language=en-US&page=1")
    fun searchSeries(@Query("query") query: String): Call<ListPaginatedResponse<Serie>>

    @GET("tv/{tv_id}/reviews?api_key=${Config.API_KEY}&language=en-US&page=1")
    fun reviewsSerie(@Path("tv_id") id: Int): Call<ListPaginatedResponse<Comment>>

    @GET("tv/{tv_id}/credits?api_key=${Config.API_KEY}&language=en-US")
    fun creditsSerie(@Path("tv_id") id: Int): Call<CreditsResponse>

    /***Season***/
    @GET("tv/{tv_id}/season/{season_number}?api_key=${Config.API_KEY}&language=en-US")
    fun seasonDetails(@Path("tv_id") serieId: Int, @Path("season_number") id: Int): Call<Season>


    /***Person***/
    @GET("person/popular?api_key=${Config.API_KEY}&language=en-US")
    fun getPersons(@Query("page") page: Int): Call<ListPaginatedResponse<Person>>

    @GET("person/{person_id}?api_key=${Config.API_KEY}&language=en-US")
    fun getPersonDetail(@Path("person_id") id: Int): Call<Person>
}