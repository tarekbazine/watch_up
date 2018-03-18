package com.example.tarekbaz.watch_up.Models

data class Serie(val title: String,
                 val discription: String,
                 val image : Int,
                 val seasons :List<Season>,
                 val comments :List<Comment>,
                 val evaluation : Float,
                 val linkedSeries : List<Serie>) {
}