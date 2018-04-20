package com.example.tarekbaz.watch_up.Models

data class Person(val name: String,
                  val picture: Int,
                  val birthDay: String,
                  val bibliography: String,
                  val movies: List<Movie>,
                  val evaluation: Double,
                  val comments: List<Comment>
                  ) {
}
