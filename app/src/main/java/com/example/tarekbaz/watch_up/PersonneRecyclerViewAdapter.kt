package com.example.tarekbaz.watch_up

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast

class PersonneRecyclerViewAdapter(private val mContext: Context,
                                  val personneNames: List<String>,
                                  val personneImages: List<Int>,
                                  val isIndicated: List<Boolean>)
    : RecyclerView.Adapter<PersonneRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_personne, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.personne_img.setImageResource(personneImages.get(position))
        holder.personne_name.setText(personneNames.get(position))
        holder.personne_is_indecated.setIsIndicator(isIndicated.get(position))

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                Toast.makeText(mContext,
                        personneImages.get(position),
                        Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return personneNames.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var personne_img: ImageView
        internal var personne_name: TextView
        internal var personne_is_indecated: RatingBar

        init {
            personne_img = itemView.findViewById(R.id.personne_image)
            personne_name = itemView.findViewById(R.id.personne_nameText)
            personne_is_indecated = itemView.findViewById(R.id.personne_ratingBar)
        }
    }

}