package com.example.tarekbaz.watch_up.Models

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity()
data class Movie(
                 @PrimaryKey var id: Int,
                 @ColumnInfo(name = "title") var title: String?,
                 @SerializedName("overview")
                 @ColumnInfo(name = "description") var description: String?,
                 @ColumnInfo(name = "image") var image: Int?,
                 @ColumnInfo(name = "trailer") var trailer: Int?,
//                 @Expose(serialize = false, deserialize = false) // equals neither serialize nor deserialize
                 @Ignore var cinemas: List<Cinema>?,
                 @Ignore var actors: List<Person>?,
                 @Ignore var directors: List<Person>?,
                 @Ignore var comments: List<Comment>?,
                 @Ignore var linkedMovies: List<Movie>?,
                 @ColumnInfo(name = "poster_path") var poster_path: String = "",
                 @ColumnInfo(name = "vote_average") var vote_average: Double = 6.6,
//                 @Ignore var release_date: Date? = Date()
                 @ColumnInfo(name = "release_date")var release_date: Date ?= Date(),
                 @ColumnInfo(name = "is_fav", index = true)var fav: Boolean? = false
                 ) {
    constructor() : this(0, "", "", 0, 0, null, null, null, null,null, "",0.0,null,false)

//    override fun hashCode(): Int {
//        return this.id
//    }
}