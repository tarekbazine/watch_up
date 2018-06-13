package com.example.tarekbaz.watch_up

import android.content.Intent
import android.widget.Toast
import android.graphics.Bitmap
import java.nio.file.Files.exists
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory



class Config{
    companion object {
        val API_BASE_URL = "https://api.themoviedb.org/3/"
        val IMG_BASE_URL = "https://image.tmdb.org/t/p/w185"
        const val API_KEY = "88866508ff0c05eb70a2cad3a137afa1"
    }

}
