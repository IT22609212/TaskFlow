package com.example.taskflow

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(
    private val activity: Activity,
    private val context: Context,
    private val _id: ArrayList<String>,
    private val task_title: ArrayList<String>,
    private val task_author: ArrayList<String>,
    private val task_number: ArrayList<String>
) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id_txt: TextView = itemView.findViewById(R.id.id_txt)
        val task_title_txt: TextView = itemView.findViewById(R.id.task_title_txt)
        val task_author_txt: TextView = itemView.findViewById(R.id.task_author_txt)
        val task_number_txt: TextView = itemView.findViewById(R.id.task_number_txt)

        var mainLayout = itemView.findViewById<LinearLayout>(R.id.mainLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id_txt.text = _id[position].toString()
        holder.task_title_txt.text = task_title[position].toString()
        holder.task_author_txt.text = task_author[position].toString()
        holder.task_number_txt.text = task_number[position].toString()

        //Recycle View
        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", _id[position].toString())
            intent.putExtra("title", task_title[position].toString())
            intent.putExtra("author", task_author[position].toString())
            intent.putExtra("pages", task_number[position].toString())
            activity.startActivityForResult(intent, 1)
        }

    }


    override fun getItemCount(): Int {
        return _id.size
    }
}
