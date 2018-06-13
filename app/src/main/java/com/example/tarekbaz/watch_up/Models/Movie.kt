package com.example.tarekbaz.watch_up.Models

import com.google.gson.annotations.SerializedName
import java.util.*


data class Movie(val id: Int,
                 val title: String,
                 @SerializedName("overview")
                 val description: String,
                 val image: Int,
                 val trailer: Int,
//                 @Expose(serialize = false, deserialize = false) // equals neither serialize nor deserialize
                 var cinemas: List<Cinema>,
                 var actors: List<Person>,
                 var directors: List<Person>,
                 var comments: List<Comment>,
                 var linkedMovies: List<Movie>,
                 var poster_path: String = "",
                 var vote_average: Double = 6.6,
                 var release_date: Date = Date(),
                 var genre_ids: List<Int> = listOf(),
                 var genresList : String = "Non genre est specifé"
) {

//    override fun hashCode(): Int {
//        return this.id
//    }
}