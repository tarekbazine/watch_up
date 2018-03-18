package com.example.tarekbaz.watch_up.Models

data class Movie(val title: String,
                 val discription: String,
                 val image : Int,
                 val cinemas : List<Cinema>,
                 val actors : List<Person>,
                 val comments : List<Comment>,
                 val linkedMovies : List<Movie>) {
}