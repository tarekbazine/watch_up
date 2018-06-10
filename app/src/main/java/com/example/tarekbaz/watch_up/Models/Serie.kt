package com.example.tarekbaz.watch_up.Models

import com.google.gson.annotations.SerializedName

data class Serie(val id: Int,
                 @SerializedName("name")
                 val title: String,
                 @SerializedName("overview")
                 val discription: String,
                 val image: Int,
                 val seasons: List<Season>,
                 var comments: List<Comment>,
                 @SerializedName("vote_average")
                 val evaluation: Double,
                 var linkedSeries: List<Serie>,
                 val backdrop_path: String = "") {
}