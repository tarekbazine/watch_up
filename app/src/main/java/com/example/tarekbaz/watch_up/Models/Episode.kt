package com.example.tarekbaz.watch_up.Models

data class Episode(val discription: String,
                   val trailer : Int,
                   val comments :List<Comment>,
                   val evaluation : Double,
                   val diffusion : List<String>) {
}