package com.example.tarekbaz.watch_up.Models

class Store {
    companion object {
        var homeFilms : ArrayList<Movie> = ArrayList()
        var homeSeries : ArrayList<Serie> = ArrayList()
        var acteurs : List<Person> = listOf()

        var preferedGenres : HashSet<Int> = HashSet()
        var favFilms : ArrayList<Movie> = ArrayList()
    }
}