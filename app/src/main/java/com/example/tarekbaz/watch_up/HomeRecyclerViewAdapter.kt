package com.example.tarekbaz.watch_up

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class HomeRecyclerViewAdapter(private val mContext: Context, names: List<String>, imageUrls: List<Int>, val isSalle: Boolean = false) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

    var mNames: List<String>? = null
    var mImageUrls: List<Int>? = null

    init {
        mNames = names
        mImageUrls = imageUrls
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View

        if (isSalle) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.card_salle_fan , parent, false)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.home_film_card, parent, false)
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(mImageUrls!!.get(position))
        holder.name.setText(mNames!!.get(position))

        holder.image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                //      Toast.makeText(mContext, mNames!!.get(position), Toast.LENGTH_SHORT).show()
//                //TODO change it
                val intent = Intent(mContext, FilmDetailActivity::class.java)
                startActivity(mContext, intent, null)
            }
        })
    }

    override fun getItemCount(): Int {
        return mImageUrls!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var image: ImageView
        internal var name: TextView

        init {
            if (isSalle) {
                image = itemView.findViewById(R.id.salle_image)
                name = itemView.findViewById(R.id.salle_name)
            }else{
                image = itemView.findViewById(R.id.image_card_film)
                name = itemView.findViewById(R.id.name_card_film)
            }

        }
    }

}
