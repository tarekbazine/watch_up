package com.example.tarekbaz.watch_up.Models

import com.google.gson.annotations.SerializedName

data class Episode(
        @SerializedName("overview")
        val discription: String,
        val trailer: Int,
        var comments: List<Comment>,
        @SerializedName("vote_average")
        val evaluation: Double,
        var diffusion: List<String>,
        val still_path: String = "",
        val name: String = "") {
}