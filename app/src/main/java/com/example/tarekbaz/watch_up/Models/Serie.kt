package com.example.tarekbaz.watch_up.Models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Serie(val id: Int,
                 @SerializedName("name")
                 val title: String,
                 @SerializedName("overview")
                 val discription: String,
                 val image: Int,
                 var seasons: List<Season>,
                 var comments: List<Comment>,
                 @SerializedName("vote_average")
                 val evaluation: Double = 6.6,
                 var linkedSeries: List<Serie>,
                 val poster_path: String = "",
                 var first_air_date: Date = Date(),
                 var episode_run_time :List<Int> = listOf(),
                 var genre_ids: List<Int> = listOf(),
                 var genresList : String = "Non genre est specifé"
) {
}