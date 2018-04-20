package com.example.tarekbaz.watch_up.Models

data class Person(val name: String,
                  val picture: Int,
                  val birthDay: String,
                  val bibliography: String,
                  var movies: List<Movie>,
                  val evaluation: Double,
                  var comments: List<Comment>
                  ) {
}
