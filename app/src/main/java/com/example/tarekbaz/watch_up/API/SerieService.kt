package com.example.tarekbaz.watch_up.API

import com.example.tarekbaz.watch_up.API.Responses.CreditsResponse
import com.example.tarekbaz.watch_up.API.Responses.ListPaginatedResponse
import com.example.tarekbaz.watch_up.Config
import com.example.tarekbaz.watch_up.Models.Comment
import com.example.tarekbaz.watch_up.Models.Serie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SerieService {

    @GET("tv/airing_today?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}")
    fun getTodayAiringSeries(@Query("page") page: Int): Call<ListPaginatedResponse<Serie>>

    @GET("tv/{tv_id}/similar?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}&page=1")
    fun relatedSeries(@Path("tv_id") id: Int): Call<ListPaginatedResponse<Serie>>

    @GET("tv/{tv_id}?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}")
    fun serieDetails(@Path("tv_id") id: Int): Call<Serie>

    @GET("search/tv?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}&page=1")
    fun searchSeries(@Query("query") query: String): Call<ListPaginatedResponse<Serie>>

    @GET("tv/{tv_id}/reviews?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}&page=1")
    fun reviewsSerie(@Path("tv_id") id: Int): Call<ListPaginatedResponse<Comment>>

    @GET("tv/{tv_id}/credits?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}")
    fun creditsSerie(@Path("tv_id") id: Int): Call<CreditsResponse>

}