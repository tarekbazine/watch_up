package com.example.tarekbaz.watch_up.Offline

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.util.Log
import com.example.tarekbaz.watch_up.Models.AssotiationMovies
import com.example.tarekbaz.watch_up.Models.Comment
import com.example.tarekbaz.watch_up.Models.Movie

@Database(entities = arrayOf(Movie::class, AssotiationMovies::class, Comment::class), version = 1)
@TypeConverters(DateTypeConverter::class)
        abstract class MovieDB : RoomDatabase() {
        abstract fun movieDAO(): MovieDAO
        abstract fun relatedMoviesDAO(): RelatedMoviesDAO
        abstract fun commentsDAO(): CommentDAO

    companion object {
        private var instance: MovieDB? = null

        fun getInstance(context: Context): MovieDB? {
            if (null == instance) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDB::class.java,"movies.db")
                        .allowMainThreadQueries()
                        .build()
                Log.i("test","bd built" )
            }
            return instance
        }
    }
    fun cleanUp() {
        instance = null
    }

}
