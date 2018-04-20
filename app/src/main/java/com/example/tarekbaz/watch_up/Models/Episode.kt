package com.example.tarekbaz.watch_up.Models

data class Episode(val discription: String,
                   val trailer : Int,
                   var comments :List<Comment>,
                   val evaluation : Double,
                   var diffusion : List<String>) {
}