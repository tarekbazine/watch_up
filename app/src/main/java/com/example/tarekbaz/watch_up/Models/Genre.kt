package com.example.tarekbaz.watch_up.Models

import android.content.Context
import android.util.Log


data class Genre(
        val id : Int,
        val name : String
) {
    companion object {
        val KEY = "PREFERRED_GENRES"

        val genresList = hashMapOf( //todo ommit duplicate
                28 to  Genre(28,"Action"),
                12 to Genre(12,"Adventure"),
                16 to Genre(16,"Animation"),
                35 to Genre(35,"Comedy"),
                80 to Genre(80,"Crime"),
                99 to Genre(99,"Documentary"),
                18 to Genre(18,"Drama"),
                10751 to Genre(10751,"Family"),
                14 to Genre(14,"Fantasy"),
                36 to Genre(36,"History"),
                27 to Genre(27,"Horror"),
                10402 to Genre(10402,"Music"),
                9648 to Genre(9648,"Mystery"),
                10749 to Genre(10749,"Romance"),
                878 to Genre(878,"Science Fiction"),
                10770 to Genre(10770,"TV Movie"),
                53 to Genre(53,"Thriller"),
                10752 to Genre(10752,"War"),
                37 to Genre(37,"Western"),
                10759 to Genre(10759,"Action & Adventure"),
                16 to Genre(16,"Animation"),
                35 to Genre(35,"Comedy"),
                80 to Genre(80,"Crime"),
                99 to Genre(99,"Documentary"),
                18 to Genre(18,"Drama"),
                10751 to Genre(10751,"Family"),
                10762 to Genre(10762,"Kids"),
                9648 to Genre(9648,"Mystery"),
                10763 to Genre(10763,"News"),
                10764 to Genre(10764,"Reality"),
                10765 to Genre(10765,"Sci-Fi & Fantasy"),
                10766 to Genre(10766,"Soap"),
                10767 to Genre(10767,"Talk"),
                10768 to Genre(10768,"War & Politics"),
                37 to Genre(37,"Western")
        )


        val movieGenres = listOf(
                Genre(28,"Action"),
                Genre(12,"Adventure"),
                Genre(16,"Animation"),
                Genre(35,"Comedy"),
                Genre(80,"Crime"),
                Genre(99,"Documentary"),
                Genre(18,"Drama"),
                Genre(10751,"Family"),
                Genre(14,"Fantasy"),
                Genre(36,"History"),
                Genre(27,"Horror"),
                Genre(10402,"Music"),
                Genre(9648,"Mystery"),
                Genre(10749,"Romance"),
                Genre(878,"Science Fiction"),
                Genre(10770,"TV Movie"),
                Genre(53,"Thriller"),
                Genre(10752,"War"),
                Genre(37,"Western")
        )

        val serieGenre = listOf(
                Genre(10759,"Action & Adventure"),
                Genre(16,"Animation"),
                Genre(35,"Comedy"),
                Genre(80,"Crime"),
                Genre(99,"Documentary"),
                Genre(18,"Drama"),
                Genre(10751,"Family"),
                Genre(10762,"Kids"),
                Genre(9648,"Mystery"),
                Genre(10763,"News"),
                Genre(10764,"Reality"),
                Genre(10765,"Sci-Fi & Fantasy"),
                Genre(10766,"Soap"),
                Genre(10767,"Talk"),
                Genre(10768,"War & Politics"),
                Genre(37,"Western")
        )


        fun initPreferredGenres(context: Context){

            val prefGenres = context.getSharedPreferences(Genre.KEY, Context.MODE_PRIVATE)
            val genres = prefGenres.getStringSet(Genre.KEY, HashSet<String>())

            Log.i("myLogiii", genres.toString())

            genres.forEach {
                Store.preferedGenres.add(it.toInt())
            }


        }
    }
}