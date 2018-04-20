package com.example.tarekbaz.watch_up.Models

data class Season(val discription: String,
                  val image : Int,
                  val epesods : List<Episode>,
                  val evaluation : Double,
                  val trailer : Int,
                  var comments : List<Comment>,
                  var linkedActors : List<Person>){
}