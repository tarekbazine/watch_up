package com.example.tarekbaz.watch_up.Models

data class Episode(val discription: String,
                   val comments :List<Comment>,
                   val evaluation : Float,
                   val diffusion : List<String>) {
}