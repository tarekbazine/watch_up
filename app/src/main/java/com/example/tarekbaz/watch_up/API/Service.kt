package com.example.tarekbaz.watch_up.API

import com.example.tarekbaz.watch_up.API.Responses.ListPaginatedResponse
import com.example.tarekbaz.watch_up.Config
import com.example.tarekbaz.watch_up.Models.Person
import com.example.tarekbaz.watch_up.Models.Season
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    /***Season***/
    @GET("tv/{tv_id}/season/{season_number}?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}")
    fun seasonDetails(@Path("tv_id") serieId: Int, @Path("season_number") id: Int): Call<Season>


    /***Person***/
    @GET("person/popular?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}")
    fun getPersons(@Query("page") page: Int): Call<ListPaginatedResponse<Person>>

    @GET("person/{person_id}?api_key=${Config.API_KEY}&language=${Config.API_LANGUAGE}")
    fun getPersonDetail(@Path("person_id") id: Int): Call<Person>
}