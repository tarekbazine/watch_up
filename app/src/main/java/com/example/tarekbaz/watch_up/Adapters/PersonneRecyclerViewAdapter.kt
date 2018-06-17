package com.example.tarekbaz.watch_up.Adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.tarekbaz.watch_up.Config
import com.example.tarekbaz.watch_up.Models.Person
import com.example.tarekbaz.watch_up.PersonneDetailActivity
import com.example.tarekbaz.watch_up.R

class PersonneRecyclerViewAdapter(private val mContext: Context, var persons: List<Person>, val isActor: Boolean)
    : RecyclerView.Adapter<PersonneRecyclerViewAdapter.ViewHolder>() {

    val fullPersonne = persons

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_personne, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //image
        if (null != persons.get(position).profile_path)
            Glide.with(mContext).load(Config.IMG_BASE_URL + persons.get(position).profile_path)
                    .into(holder.personne_img)
        else
            holder.personne_img.setImageResource(R.drawable.no_avatar)

        holder.personne_name.setText(persons.get(position).name)
//       if (isIndicated.get(position))
//        holder.personne_is_indecated.rating = 1F
//        else
//           holder.personne_is_indecated.rating = 0F

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
//                Toast.makeText(mContext,
//                        position,
//                        Toast.LENGTH_SHORT).show()
                val intent = Intent(mContext, PersonneDetailActivity::class.java)
                intent.putExtra("index", position)
                intent.putExtra("isActor", isActor)
                ContextCompat.startActivity(mContext, intent, null)
            }
        })
    }

    override fun getItemCount(): Int {
        return persons.size
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
        persons = fullPersonne.filter { person -> person.name.contains(keyWords, true) }
        notifyDataSetChanged()
    }

}