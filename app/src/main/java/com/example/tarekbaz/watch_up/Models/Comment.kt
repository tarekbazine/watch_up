package com.example.tarekbaz.watch_up.Models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "comment")
data class Comment(@ColumnInfo(name = "author") var author : String?,
                   @ColumnInfo(name = "date") var date : String?,
                   @Ignore var filmName: String,
                   @ColumnInfo(name = "content") var content : String,
                   @ColumnInfo(name = "film_id") var filmId: Int? = 0) {
    @PrimaryKey (autoGenerate = true) var code: Int = 0
    constructor() : this("", "", "", "content", 0)

}