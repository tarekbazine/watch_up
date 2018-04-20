package com.example.tarekbaz.watch_up

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.tarekbaz.watch_up.Models.Comment

class CommentRecyclerViewAdapter(private val mContext: Context, val comments :List<Comment> )
    : RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_comment, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.userName.text = comments.get(position).filmName
//            holder.userImage.setImageResource(userImages.get(position))
            holder.comment.setText(comments.get(position).body)
//            holder.commentFor.setText(commentFor.get(position))
            holder.commentDate.setText(comments.get(position).date)
//            var formatter: DateFormat = SimpleDateFormat("HH:mm")
//            holder.film_date.setText(formatter.format(times!!.get(position)))

//            holder.itemView.setOnClickListener(object : View.OnClickListener {
//                override fun onClick(view: View) {
//                    Toast.makeText(mContext,
//                            userNames.get(position),
//                            Toast.LENGTH_SHORT).show()
//                }
//            })
        }

        override fun getItemCount(): Int {
            return this.comments.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            internal var userName: TextView
            internal var userImage: ImageView
            internal var comment: TextView
//            internal var commentFor: TextView
            internal var commentDate: TextView

            init {
                userName = itemView.findViewById(R.id.profile_name)
                userImage = itemView.findViewById(R.id.profile_image)
                comment = itemView.findViewById(R.id.comment)
//                commentFor = itemView.findViewById(R.id.film_date)
                commentDate = itemView.findViewById(R.id.comment_date)
            }
        }
}