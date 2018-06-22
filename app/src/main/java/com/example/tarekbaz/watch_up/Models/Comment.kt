package com.example.tarekbaz.watch_up.Models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity()
data class Comment(@ColumnInfo(name = "author") var author : String?,
                   @ColumnInfo(name = "date") var date : String?,
                   @ColumnInfo(name = "film") var filmName: String,
                   @ColumnInfo(name = "content") var content : String) {
    @PrimaryKey (autoGenerate = true) var id: Int = 0
}