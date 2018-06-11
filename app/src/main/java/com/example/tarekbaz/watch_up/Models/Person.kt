package com.example.tarekbaz.watch_up.Models

import java.util.*

data class Person(
        val id: Int,
        val name: String,
        val picture: Int,
        var birthday: Date,
        var gender:Int,
        var biography: String,
        var place_of_birth: String,
        val profile_path: String,
        var known_for: List<Movie>,
        val popularity: Double
        , var comments: List<Comment>) {
        }
