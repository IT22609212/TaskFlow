package com.example.taskflow

import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskflow.MyDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var add_button: FloatingActionButton
   private lateinit var empty_imageview: ImageView
    private lateinit var no_data: TextView

    private lateinit var myDB: MyDatabase
    private var task_id = ArrayList<String>()
    private var task_title = ArrayList<String>()
    private var task_author = ArrayList<String>()
    private var task_number = ArrayList<String>()
    private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        add_button = findViewById(R.id.floatingActionButton)
       empty_imageview = findViewById(R.id.empty_imageview) // Initialize empty_imageview
       no_data = findViewById(R.id.no_data) // Initialize no_data

        add_button.setOnClickListener {
            val intent = Intent(this@MainActivity, Add::class.java)
            startActivity(intent)
            finish()
        }

        myDB = MyDatabase(this@MainActivity)
        storeDataInArrays()

        customAdapter = CustomAdapter(this@MainActivity, this@MainActivity, task_id, task_title, task_author, task_number)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            recreate()
        }
    }

    private fun storeDataInArrays() {
        val cursor: Cursor? = myDB.readAllData()
        if (cursor != null) {
            if (cursor.count == 0) {
                empty_imageview.visibility = View.VISIBLE
                no_data.visibility = View.VISIBLE
            } else {
                while (cursor.moveToNext()) {
                    task_id.add(cursor.getString(0))
                    task_title.add(cursor.getString(1))
                    task_author.add(cursor.getString(2))
                    task_number.add(cursor.getString(3))
                }
                empty_imageview.visibility = View.GONE
                no_data.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.delete_all) {
            confirmDialog()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to delete all Data?")
        builder.setPositiveButton("Yes") { dialogInterface, i ->
            val myDB = MyDatabase(this@MainActivity)
            myDB.deleteAllData()
            //Refresh Activity
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") { dialogInterface, i -> }
        builder.create().show()
    }
}
