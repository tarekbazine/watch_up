package com.example.tarekbaz.watch_up.Models

data class Cinema(val id: Int,
                  val name: String,
                  val image: Int,
                  val openningTime: String,
                  val address: String,
                  val latLang: String,
                  val siteWeb : String,
                  val tel : String
//                  val movies: List<Movie>,
                  ) {
}