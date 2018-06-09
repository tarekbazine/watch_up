package com.example.tarekbaz.watch_up.Models

import com.google.gson.annotations.SerializedName

data class Movie(val id: Int,
                 val title: String,
                 @SerializedName("overview")
                 val description: String,
                 val image : Int,
                 val trailer : Int,
                 var cinemas : List<Cinema>,
                 var actors : List<Person>,
                 var directors : List<Person>,
                 var comments : List<Comment>,
                 var linkedMovies : List<Movie>,
                 var backdrop_path : String = ""
                 ) {

//    override fun hashCode(): Int {
//        return this.id
//    }
}