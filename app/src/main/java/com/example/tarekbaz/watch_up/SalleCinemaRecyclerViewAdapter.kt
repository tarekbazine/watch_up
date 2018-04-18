package com.example.tarekbaz.watch_up

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class SalleCinemaRecyclerViewAdapter(private val mContext: Context,
                                     var salleNames: List<String>,
                                     val salleImages: List<Int>,
                                     val salleAddress: List<String>,
                                     val salleOpennings: List<String>)
    : RecyclerView.Adapter<SalleCinemaRecyclerViewAdapter.ViewHolder>() {

    var fullSalles = salleNames

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_salle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.salle_image.setImageResource(salleImages.get(position))
        holder.salle_name.setText(salleNames.get(position))
        holder.salle_address.setText(salleAddress.get(position))
        holder.salle_opening.setText(salleOpennings.get(position))
//            var formatter: DateFormat = SimpleDateFormat("HH:mm")
//            holder.time.setText(formatter.format(times!!.get(position)))

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                Toast.makeText(mContext,
                        salleNames.get(position),
                        Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return salleNames.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var salle_address: TextView
        internal var salle_opening: TextView
        internal var salle_name: TextView
        internal var salle_image: ImageView

        init {
            salle_address = itemView.findViewById(R.id.salle_address)
            salle_opening = itemView.findViewById(R.id.salle_opening)
            salle_name = itemView.findViewById(R.id.salle_name)
            salle_image = itemView.findViewById(R.id.salle_image)
        }
    }

    fun filter(keyWords: String) {
        salleNames = fullSalles.filter { salleName -> salleName.contains(keyWords, true) }
        notifyDataSetChanged()
    }
}