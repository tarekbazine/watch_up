package com.example.tarekbaz.watch_up.Models

data class Movie(val title: String,
                 val description: String,
                 val image : Int,
                 val trailer : Int,
                 var cinemas : List<Cinema>,
                 var actors : List<Person>,
                 var directors : List<Person>,
                 var comments : List<Comment>,
                 var linkedMovies : List<Movie>) {
}