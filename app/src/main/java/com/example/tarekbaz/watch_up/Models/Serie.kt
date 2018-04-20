package com.example.tarekbaz.watch_up.Models

data class Serie(val title: String,
                 val discription: String,
                 val image: Int,
                 val seasons:List<Season>,
                 var comments:List<Comment>,
                 val evaluation: Double,
                 var linkedSeries: List<Serie>) {
}