package com.example.tarekbaz.watch_up

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class __apiEx : AppCompatActivity() {


    private fun initRecyclerView(todos : List<Todo>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.setLayoutManager(layoutManager)
        val adapter_films = TodoRecyclerViewAdapter(this, todos)
        recycler.setAdapter(adapter_films)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initTodos()

    }

    private fun initTodos(){


        // create an instance of gson to be used when building our service
        val gson = GsonBuilder().create()

// use retrofit to create an instance of BookService
        val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val service = retrofit.create<BlogService>(BlogService::class.java!!)





// call the method we defined in our interface, and handle the result
        service.getTodos().enqueue(object: Callback<List<Todo>> {

            override fun onResponse(call: Call<List<Todo>>, response: retrofit2.Response<List<Todo>>?) {
                if ((response != null) && (response.code() == 200)) {

                    var todos = response.body()


                    Log.i("dd", ""+todos!![0].title + ""+ todos!![0].completed )
                    Log.i("dd", ""+todos!![2].title + ""+ todos!![2].completed )
                    Log.i("dd", ""+todos.size )
//                    txtTitle.setText(post!!.title)
//                    txtBody.setText(post!!.body)
                    Toast.makeText(baseContext, "Succès", Toast.LENGTH_LONG).show()

                    initRecyclerView(todos)
                }

            }

            override fun onFailure(call: Call<List<Todo>>?, t: Throwable?){
                Toast.makeText(baseContext, "Echec", Toast.LENGTH_LONG).show()
            }
        })

    }
}
