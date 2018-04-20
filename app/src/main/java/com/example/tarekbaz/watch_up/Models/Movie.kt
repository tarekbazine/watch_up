package com.example.tarekbaz.watch_up.Models

data class Movie(val title: String,
                 val description: String,
                 val image : Int,
                 val trailer : Int,
                 val cinemas : List<Cinema>,
                 val actors : List<Person>,
                 val directors : List<Person>,
                 val comments : List<Comment>,
                 val linkedMovies : List<Movie>) {
}