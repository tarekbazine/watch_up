package com.example.tarekbaz.watch_up

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SalleRecyclerViewAdapter (private val mContext: Context, salle_names: List<String>, times: List<Date>) : RecyclerView.Adapter<com.example.tarekbaz.watch_up.SalleRecyclerViewAdapter.ViewHolder>() {

        var salle_names: List<String>? = null
        var times: List<Date>? = null

        init {
            this.salle_names = salle_names
            this.times = times
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.film_salle_card, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.salle_name.setText(salle_names!!.get(position))
            var formatter: DateFormat = SimpleDateFormat("HH:mm")
            holder.time.setText(formatter.format(times!!.get(position)))

            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    Toast.makeText(mContext, salle_names!!.get(position), Toast.LENGTH_SHORT).show()
                }
            })
        }

        override fun getItemCount(): Int {
            return salle_names!!.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            internal var time: TextView
            internal var salle_name: TextView

            init {
                time = itemView.findViewById(R.id.timeText)
                salle_name = itemView.findViewById(R.id.nameSalleText)
            }
        }
}