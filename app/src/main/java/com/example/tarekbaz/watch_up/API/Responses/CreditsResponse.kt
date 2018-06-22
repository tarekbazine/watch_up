package com.example.tarekbaz.watch_up.API.Responses

import com.example.tarekbaz.watch_up.Models.Person

data class CreditsResponse(
        var cast : List<Person>,
        var crew : List<Person>
) {
    companion object {
        val DIRECTOR = "Director"
    }
}