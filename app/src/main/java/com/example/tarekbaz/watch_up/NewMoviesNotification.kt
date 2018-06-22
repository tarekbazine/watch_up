package com.example.tarekbaz.watch_up

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.tarekbaz.watch_up.Models.Genre
import com.example.tarekbaz.watch_up.Models.Movie
import com.example.tarekbaz.watch_up.Models.ResponsesAPI.MoviesResponse
import com.example.tarekbaz.watch_up.Models.Service
import com.example.tarekbaz.watch_up.Models.Store
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class NewMoviesNotification {

    companion object {

        val ALREADY_SEEN = "ALREADY_SEEN"

        fun create(context: Context) {
            if (Store.preferedGenres.isEmpty()) return

            val gson = GsonBuilder().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(Config.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            val service = retrofit.create<Service>(Service::class.java!!)

            val prefGenres = context.getSharedPreferences(Genre.KEY, Context.MODE_PRIVATE)
            val filmAlreadySeen = prefGenres.getStringSet(ALREADY_SEEN, HashSet<String>())


            val keys = ArrayList(Store.preferedGenres)
            var genresList = keys[0].toString()
            for (i in 1 until keys.size) {
                genresList += "|" + keys[i]
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val cal = Calendar.getInstance()
            val date_bonr_inf = sdf.format(cal.time)
            cal.add(Calendar.DATE, 7)
            val date_bonr_sup = sdf.format(cal.time)

            service.latestMovies(date_bonr_inf.toString(), date_bonr_sup.toString(), genresList)
                    .enqueue(object : Callback<MoviesResponse> {
                        override fun onResponse(call: Call<MoviesResponse>, response: retrofit2.Response<MoviesResponse>?) {
                            if ((response != null) && (response.code() == 200
                                            && !response.body()!!.results.isEmpty())) {


                                val latestMovies = response.body()!!.results

                                Log.i("myLogiii", "from service ")

                                var latestMovie : Movie? = null

                                //todo test if the film is alredy seen
                                for (i in 0 until latestMovies.size){
                                    if (!filmAlreadySeen.contains(latestMovies[i].id.toString())){
                                        latestMovie = latestMovies[i]
                                        break
                                    }
                                }

                                if (latestMovie == null) {
                                    Toast.makeText(context, "No up movie " + response.toString()
                                            , Toast.LENGTH_LONG).show()
                                    return
                                }

                                val editor = prefGenres.edit()

                                filmAlreadySeen.add(latestMovie.id.toString())

                                //todo
                                editor.putStringSet(ALREADY_SEEN, null)
                                editor.commit()

                                editor.putStringSet(ALREADY_SEEN, filmAlreadySeen)
                                editor.commit()

                                Store.homeFilms.add(latestMovie)
                                initNotificatioData(latestMovie, context)
                            } else if (response != null) {
                                Toast.makeText(context, "No up movie " + response.toString()
                                        , Toast.LENGTH_LONG).show()
                                Log.i("myLogi333", "" + response.toString())

                            }
                        }

                        override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?) {
                            Toast.makeText(context, "Echec Noty", Toast.LENGTH_LONG).show()
                        }
                    })

        }


        fun createFromService(context: Context) {
            val gson = GsonBuilder().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(Config.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            val service = retrofit.create<Service>(Service::class.java)

            val prefGenres = context.getSharedPreferences(Genre.KEY, Context.MODE_PRIVATE)
            val genres = prefGenres.getStringSet(Genre.KEY, HashSet<String>())
            if (genres.isEmpty()) return
            val filmAlreadySeen = prefGenres.getStringSet(ALREADY_SEEN, HashSet<String>())

            Log.i("myLogiii1122", genres.toString())
            Log.i("myLogiii1122 film ", filmAlreadySeen.toString())

            val keys = ArrayList(genres)
            var genresList = keys[0].toString()
            for (i in 1 until keys.size) {
                genresList += "|" + keys[i]
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val cal = Calendar.getInstance()
            val date_bonr_inf = sdf.format(cal.time)
            cal.add(Calendar.DATE, 7)
            val date_bonr_sup = sdf.format(cal.time)

            service.latestMovies(date_bonr_inf.toString(), date_bonr_sup.toString(), genresList)
                    .enqueue(object : Callback<MoviesResponse> {
                        override fun onResponse(call: Call<MoviesResponse>, response: retrofit2.Response<MoviesResponse>?) {
                            if ((response != null) && (response.code() == 200
                                            && !response.body()!!.results.isEmpty())) {
                                val latestMovies = response.body()!!.results

                                Log.i("myLogiii", "from service ")

                                var latestMovie : Movie? = null

                                //todo test if the film is alredy seen
                                for (i in 0 until latestMovies.size){
                                    if (!filmAlreadySeen.contains(latestMovies[i].id.toString())){
                                        latestMovie = latestMovies[i]
                                        break
                                    }
                                }

                                if (latestMovie == null) {
                                    Toast.makeText(context, "No up movie " + response.toString()
                                            , Toast.LENGTH_LONG).show()
                                    return
                                }

                                val editor = prefGenres.edit()

                                filmAlreadySeen.add(latestMovie.id.toString())

                                //todo
                                editor.putStringSet(ALREADY_SEEN, null)
                                editor.commit()

                                editor.putStringSet(ALREADY_SEEN, filmAlreadySeen)
                                editor.commit()

                                initNotificatioData(latestMovie, context)
                            } else if (response != null) {
                                Toast.makeText(context, "No up movie " + response.toString()
                                        , Toast.LENGTH_LONG).show()
                                Log.i("myLogi333", "" + response.toString())

                            }
                        }

                        override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?) {
                            Toast.makeText(context, "Echec Noty", Toast.LENGTH_LONG).show()
                        }
                    })
        }

        fun initNotificatioData(film: Movie, context: Context) {
            Glide.with(context)
                    .asBitmap()
                    .load(Config.IMG_BASE_URL + film.poster_path)
                    .into(object : SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            createNotification(resource, film, context)
                        }
                    })
        }

        @SuppressLint("NewApi")
        private fun createNotification(bitmap: Bitmap, film: Movie, context: Context) {
            // Create an explicit intent for an Activity in your app
            val intent = Intent(context, FilmDetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("index", film.id)
            intent.putExtra("fromNotification", true)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

//        Store.homeFilms.add(films.get(position))

            val CHANNEL_ID = "CHANNEL_WU_01"
            val notificationId = 1

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        CharSequence name = getString(CHANNEL_ID);
//        String description = getString(R.string.channel_description);
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, "CHANNEL_WU_NAME", importance)
//        channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                val notificationManager = context.getSystemService(NotificationManager::class.java!!)
                notificationManager.createNotificationChannel(channel)
            }

            val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.star)
                    .setLargeIcon(bitmap)
                    .setContentTitle(film.title)
                    .setContentText("Premium show: ${SimpleDateFormat("E dd MMM yyyy").format(film.release_date)}")
                    .setStyle(NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)
                            .setBigContentTitle(film.title)
                            .setSummaryText("Premium show: ${SimpleDateFormat("E dd MMM yyyy").format(film.release_date)}")
                    )
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)


            //run
            val notificationManager = NotificationManagerCompat.from(context)
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mBuilder.build())

        }

        val SERVICE_ID = 9

        val KEY = "ALARMSHP"
        val DAY = "DAY"

        fun startAlarmService(context: Context) {

            val sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val cal = Calendar.getInstance()
            val todayDate = sdf.format(cal.time)

            val lastDaySharedSet = sp.getString(DAY, "2000-02-20")

//            if (todayDate.toString() != lastDaySharedSet) {//todo
//            val editor = sp.edit()
//            editor.putString(DAY, todayDate.toString())
//            editor.commit()

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR))
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE))
            calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND))

            Log.i("myLogiii", "service started " + Build.VERSION.SDK_INT)
            val intent = Intent(context, AlarmReceiver::class.java)

//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            val pintent = PendingIntent.getBroadcast(
                    context, SERVICE_ID, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
            //todo PendingIntent.FLAG_CANCEL_CURRENT
            val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
//    //                    calendar.timeInMillis,
//                        System.currentTimeMillis()+60000,
//                        pintent)
//            }else {
            alarm.setRepeating(AlarmManager.RTC_WAKEUP,
//                    calendar.timeInMillis,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES/*todo*/,
                    pintent)
//            }

            Log.i("myLogiii", "already service started ")

//            AlarmManager.INTERVAL_DAY
//            System.currentTimeMillis()

//            }

        }


//
//        val HOUR = "HOUR"
//        val MIN = "MIN"
//        val SEC = "SEC"
//
//        fun setAlarmTime(context: Context) {
//            val sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
//
//            val sdf = SimpleDateFormat("yyyy-MM-dd")
//            val cal = Calendar.getInstance()
//            val todayDate = sdf.format(cal.time)
//
//            val lastDaySharedSet = sp.getString(DAY, "2000-02-20")
//
//            if (todayDate.toString() != lastDaySharedSet) {
//                val editor = sp.edit()
//                editor.putInt(HOUR, cal.get(Calendar.HOUR_OF_DAY))
//                editor.putInt(MIN, cal.get(Calendar.MINUTE))
//                editor.putInt(SEC, cal.get(Calendar.SECOND))
//                editor.commit()
//            }
//        }

//        Context ctx = getApplicationContext();
//        /** this gives us the time for the first trigger.  */
//        Calendar cal = Calendar.getInstance();
//        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
//        long interval = 1000 * 60 * 5; // 5 minutes in milliseconds
//        Intent serviceIntent = new Intent(ctx, NewsCheckingService.class);
//// make sure you **don't** use *PendingIntent.getBroadcast*, it wouldn't work
//        PendingIntent servicePendingIntent =
//        PendingIntent.getService(ctx,
//        NewsCheckingService.SERVICE_ID, // integer constant used to identify the service
//        serviceIntent,
//        PendingIntent.FLAG_CANCEL_CURRENT);  // FLAG to avoid creating a second service if there's already one running
//// there are other options like setInexactRepeating, check the docs
//        am.setRepeating(
//        AlarmManager.RTC_WAKEUP,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
//        cal.getTimeInMillis(),
//        interval,
//        servicePendingIntent
//        );
    }
}