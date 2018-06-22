package com.example.tarekbaz.watch_up

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.widget.Toast


class AlarmReceiver :  BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
//        Log.i("myLogiii", "noty")

        Toast.makeText(context.applicationContext,"alarm set",Toast.LENGTH_SHORT).show()

        //auto start noty after boot
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            NewMoviesNotification.startAlarmService(context)
            return
        }

//        doAsync {
            //Trigger the notification
            NewMoviesNotification.createFromService(context)
//        }.execute()

    }

    class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            handler()
            return null
        }
    }
}
