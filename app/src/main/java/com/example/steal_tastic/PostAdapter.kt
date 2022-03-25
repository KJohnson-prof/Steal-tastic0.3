package com.example.steal_tastic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(val context: Context, val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post,parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int){
        val post = posts.get(position)
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ivImage: ImageView
        val tvItem: TextView
        val tvAddress: TextView
        val tvDescription: TextView
        val tvTag: TextView

        init{
            ivImage = itemView.findViewById(R.id.ivImage)
            tvItem = itemView.findViewById(R.id.tvItem)
            tvAddress = itemView.findViewById(R.id.tvAddress)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            tvTag = itemView.findViewById(R.id.tvTag)
        }

        fun bind(post: Post){
            tvItem.text = post.getItemName()
            tvAddress.text = post.getAddress()
            tvDescription.text = post.getDescription()
            tvTag.text = post.getTagList().toString()

            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)
        }
}


}