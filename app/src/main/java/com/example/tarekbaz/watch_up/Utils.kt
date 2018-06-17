package com.example.tarekbaz.watch_up

import android.app.PendingIntent.getActivity
import android.content.Context
import android.support.v7.app.AlertDialog
import com.google.gson.*
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        //this method is used to solve the problem on empty date ""
        fun getGson(): Gson {
            val gsonBuilder = GsonBuilder()

            gsonBuilder.registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
                internal var df = SimpleDateFormat("yyyy-MM-dd")
                @Throws(JsonParseException::class)
                override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date? {
                    try {
                        return df.parse(json.asString)
                    } catch (e: ParseException) {
                        return null
                    }

                }
            })

            return (gsonBuilder.create())
        }
    }
}