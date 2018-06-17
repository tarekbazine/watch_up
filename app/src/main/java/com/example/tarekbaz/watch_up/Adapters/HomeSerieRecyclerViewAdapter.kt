package com.example.tarekbaz.watch_up.Adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.tarekbaz.watch_up.Config
import com.example.tarekbaz.watch_up.Models.Serie
import com.example.tarekbaz.watch_up.R
import com.example.tarekbaz.watch_up.SerieDetailActivity


class HomeSerieRecyclerViewAdapter(private val mContext: Context, val series: List<Serie>) : RecyclerView.Adapter<HomeSerieRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_home_film, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (null != series.get(position).poster_path)
            Glide.with(mContext)
                    .load(Config.IMG_BASE_URL + series.get(position).poster_path)
                    .into(holder.image)
        else
            holder.image.setImageResource(R.drawable.no_img2)

        holder.name.setText(series.get(position).title)

        holder.image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                //      Toast.makeText(mContext, mNames!!.get(position), Toast.LENGTH_SHORT).show()
//                //TODO change it
                val intent = Intent(mContext, SerieDetailActivity::class.java)
                intent.putExtra("_id", series.get(position).id)
                startActivity(mContext, intent, null)
            }
        })
    }

    override fun getItemCount(): Int {
        return series.size
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
