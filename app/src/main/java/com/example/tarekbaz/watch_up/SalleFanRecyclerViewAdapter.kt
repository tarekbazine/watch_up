package com.example.tarekbaz.watch_up

import android.content.Context
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.tarekbaz.watch_up.Models.Cinema


class SalleFanRecyclerViewAdapter(private val mContext: Context, val salles: List<Cinema>) : RecyclerView.Adapter<SalleFanRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_salle_fan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(salles.get(position).image)
        holder.name.setText(salles.get(position).name)

        holder.image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                //      Toast.makeText(mContext, mNames!!.get(position), Toast.LENGTH_SHORT).show()
//                //TODO change it
//                val intent = Intent(mContext, ::class.java)
//                intent.putExtra("index", position)
//                startActivity(mContext, intent, null)
            }
        })
    }

    override fun getItemCount(): Int {
        return salles.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var image: ImageView
        internal var name: TextView

        init {
            image = itemView.findViewById(R.id.salle_image)
            name = itemView.findViewById(R.id.salle_name)
        }
    }

}
