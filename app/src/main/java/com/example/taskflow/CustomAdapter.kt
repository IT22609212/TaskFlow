package com.example.taskflow

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(private val activity: Activity, private val context: Context,
                    private val _id: ArrayList<String>, private val task_title: ArrayList<String>,
                    private val task_author: ArrayList<String>, private val task_number: ArrayList<String>)
    : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder._id_txt.text = _id[position]
        holder.task_title_txt.text = task_title[position]
        holder.task_author_txt.text = task_author[position]
        holder.task_number_txt.text = task_number[position]
        // RecyclerView onClickListener
        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", _id[position])
            intent.putExtra("title", task_title[position])
            intent.putExtra("author", task_author[position])
            intent.putExtra("number", task_number[position])
            activity.startActivityForResult(intent, 1)
        }
    }

    override fun getItemCount(): Int {
        return _id.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _id_txt: TextView = itemView.findViewById(R.id.id_txt)
        var task_title_txt: TextView = itemView.findViewById(R.id.task_title_txt)
        var task_author_txt: TextView = itemView.findViewById(R.id.task_author_txt)
        var task_number_txt: TextView = itemView.findViewById(R.id.task_number_txt)
        var mainLayout: LinearLayout = itemView.findViewById(R.id.mainLayout)

//        init {
//            // Animate RecyclerView
//            val translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim)
//            mainLayout.animation = translate_anim
//        }
    }
}
