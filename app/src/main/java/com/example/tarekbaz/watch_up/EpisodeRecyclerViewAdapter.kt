package com.example.tarekbaz.watch_up

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.tarekbaz.watch_up.Models.Episode
import com.example.tarekbaz.watch_up.Models.Serie


class EpisodeRecyclerViewAdapter(private val mContext: Context, val episodes: List<Episode>, val seasonId : Int,val serieId: Int) : RecyclerView.Adapter<EpisodeRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_home_film, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.image.setImageResource(episodes.get(position).) todo
        holder.name.setText("Episode "+position)

        holder.image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                //      Toast.makeText(mContext, mNames!!.get(position), Toast.LENGTH_SHORT).show()
//                //TODO change it
                val intent = Intent(mContext, EpisodeDetailActivity::class.java)
                intent.putExtra("indexSerie",serieId)
                intent.putExtra("indexSeason",seasonId)
                intent.putExtra("index",position)
                startActivity(mContext, intent, null)
            }
        })
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var image: ImageView
        internal var name: TextView

        init {
                image = itemView.findViewById(R.id.image_card_film)
                name = itemView.findViewById(R.id.name_card_film)
        }
    }

}
