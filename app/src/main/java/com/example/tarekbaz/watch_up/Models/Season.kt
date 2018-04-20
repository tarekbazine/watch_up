package com.example.tarekbaz.watch_up.Models

data class Season(val discription: String,
                  val image : Int,
                  val epesods : List<Episode>,
                  val evaluation : Double,
                  val trailer : Int,
                  val comments : List<Comment>,
                  val linkedActors : List<Person>){
}