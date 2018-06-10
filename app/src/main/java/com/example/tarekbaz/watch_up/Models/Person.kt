package com.example.tarekbaz.watch_up.Models

data class Person(
                  val id: Int,
                  val name: String,
                  val picture: Int,
                  val birthday: String,
                  val gender:Int,
                  val biography: String,
                  val place_of_birth: String,
                  val profile_path: String,
                  var known_for: List<Movie>,
                  val popularity: Double
                  ,var comments: List<Comment>
                  ) {
}
