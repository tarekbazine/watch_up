package com.example.tarekbaz.watch_up.Models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.NO_ACTION


// Solution traditionelle
@Entity(tableName = "movies_related_movies",  primaryKeys = arrayOf( "movie_id", "related_movie_id" ),
        foreignKeys = arrayOf(
            ForeignKey(entity = Movie::class, parentColumns = arrayOf("id"), childColumns = arrayOf("movie_id"), onDelete = NO_ACTION),
            ForeignKey(entity = Movie::class , parentColumns = arrayOf("id"), childColumns = arrayOf("related_movie_id"),onDelete = NO_ACTION)
))

class AssotiationMovies (
    @ColumnInfo(name = "movie_id") var movieId: Int = 0,
    @ColumnInfo(name = "related_movie_id") var relatedMovieId: Int = 0
) {

    override fun toString(): String {
        return (this.movieId.toString() + " == " + this.relatedMovieId.toString())
    }
}