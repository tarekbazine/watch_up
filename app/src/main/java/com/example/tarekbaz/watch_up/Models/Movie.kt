package com.example.tarekbaz.watch_up.Models

import com.example.tarekbaz.watch_up.Models.Mocker.getRandomElements
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose


data class Movie(val id: Int,
                 val title: String,
                 @SerializedName("overview")
                 val description: String,
                 val image : Int,
                 val trailer : Int,
//                 @Expose(serialize = false, deserialize = false) // equals neither serialize nor deserialize
                 var cinemas : List<Cinema>,
                 var actors : List<Person>,
                 var directors : List<Person>,
                 var comments : List<Comment>,
                 var linkedMovies : List<Movie>,
                 var poster_path : String = ""
                 ) {

//    override fun hashCode(): Int {
//        return this.id
//    }
}