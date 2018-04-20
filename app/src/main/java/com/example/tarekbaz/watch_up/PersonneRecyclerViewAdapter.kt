package com.example.tarekbaz.watch_up

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class PersonneRecyclerViewAdapter(private val mContext: Context,
                                  var personneNames: List<String>,
                                  val personneImages: List<Int>,
                                  val isIndicated: List<Boolean>)
    : RecyclerView.Adapter<PersonneRecyclerViewAdapter.ViewHolder>() {

    val fullPersonne = personneNames
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_personne, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.personne_img.setImageResource(personneImages.get(position))
        holder.personne_name.setText(personneNames.get(position))
       if (isIndicated.get(position))
        holder.personne_is_indecated.setBackgroundResource(R.drawable.active_star)
        else
           holder.personne_is_indecated.setBackgroundResource(R.drawable.inactive_star)

        // Change favour button
        holder.personne_is_indecated.setOnClickListener(View.OnClickListener { view ->
            //TODO personnes favour
            if ( isIndicated.get(position)){
                // Remove from favour
                holder.personne_is_indecated.setBackgroundResource(R.drawable.inactive_star)
            }else{
                // add to favour
                holder.personne_is_indecated.setBackgroundResource(R.drawable.active_star)
            }
        })

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
        internal var personne_is_indecated: ImageButton

        init {
            personne_img = itemView.findViewById(R.id.personne_image)
            personne_name = itemView.findViewById(R.id.personne_nameText)
            personne_is_indecated = itemView.findViewById(R.id.starButton)
        }
    }

    fun filter(keyWords: String) {
        personneNames = fullPersonne.filter { personName -> personName.contains(keyWords, true) }
        notifyDataSetChanged()
    }

}