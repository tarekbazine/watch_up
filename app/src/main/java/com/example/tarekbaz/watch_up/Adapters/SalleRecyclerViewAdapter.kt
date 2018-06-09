package com.example.tarekbaz.watch_up.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tarekbaz.watch_up.Models.Cinema
import com.example.tarekbaz.watch_up.R
import java.text.DateFormat
import java.text.SimpleDateFormat

class SalleRecyclerViewAdapter (private val mContext: Context, val salles : List<Cinema>) : RecyclerView.Adapter<SalleRecyclerViewAdapter.ViewHolder>() {



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_film_salle, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.salle_name.setText(salles.get(position).name)
            var formatter: DateFormat = SimpleDateFormat("HH:mm")
            holder.time.setText(salles.get(position).openningTime)

            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
//                    Toast.makeText(mContext, salle_names!!.get(position), Toast.LENGTH_SHORT).show()
                }
            })
        }

        override fun getItemCount(): Int {
            return salles.size
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