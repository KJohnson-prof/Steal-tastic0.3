package com.example.steal_tastic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parse.ParseUser

class TagAdapter(val tagList: List<String>,
    val longClickListener: OnLongClickListener):
    RecyclerView.Adapter<TagAdapter.ViewHolder>(){

    //Getting the user and adding tag to them
    val user = ParseUser()

    //define an interface for the long click
    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagAdapter.ViewHolder {
        //Getting context
        val context = parent.context

        val inflater = LayoutInflater.from(context)

        val contactView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false)

        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: TagAdapter.ViewHolder, position: Int) {
        val item = tagList.get(position)
        holder.textView.text = item.toString()





    }

    override fun getItemCount(): Int {
        return tagList.size
    }

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        val textView: TextView

        init{
            textView = itemView.findViewById(android.R.id.text1)

            //represents a single line in our tag list
            itemView.setOnClickListener{
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }

}