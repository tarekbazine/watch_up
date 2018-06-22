package com.example.tarekbaz.watch_up.API

import com.example.tarekbaz.watch_up.API.Responses.CreditsResponse
import com.example.tarekbaz.watch_up.API.Responses.ListPaginatedResponse
import com.example.tarekbaz.watch_up.Config
import com.example.tarekbaz.watch_up.Models.Comment
import com.example.tarekbaz.watch_up.Models.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {

    @GET("movie/upcoming?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}")
    fun getHomeMovies(@Query("page") page: Int): Call<ListPaginatedResponse<Movie>>

    @GET("movie/{movie_id}?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}")
    fun movieDetails(@Path("movie_id") id: Int): Call<Movie>

    @GET("movie/{movie_id}/similar?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}&page=1")
    fun relatedMovies(@Path("movie_id") id: Int): Call<ListPaginatedResponse<Movie>>

    @GET("movie/{movie_id}/reviews?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}&page=1")
    fun reviewsMovie(@Path("movie_id") id: Int): Call<ListPaginatedResponse<Comment>>

    @GET("movie/{movie_id}/credits?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}")
    fun creditsMovie(@Path("movie_id") id: Int): Call<CreditsResponse>

    @GET("search/movie?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}&page=1")
    fun searchMovies(@Query("query") query: String): Call<ListPaginatedResponse<Movie>>

    @GET("discover/movie?sort_by=release_date.desc&api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}&page=1")
    fun latestMovies(//getLatestMovies
            @Query("release_date.gte") date_inf: String,
            @Query("release_date.lte") date_sup: String,
            @Query("with_genres") genre_ids: String
    ): Call<ListPaginatedResponse<Movie>>
}