package com.example.taskflow
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var myDB: MyDatabase
    private val _id = ArrayList<String>()
    private val task_title = ArrayList<String>()
    private val task_author = ArrayList<String>()
    private val task_number = ArrayList<String>()

    private lateinit var customAdapter: CustomAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyclerView)

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener{
            val intent = Intent(this@MainActivity, Add::class.java)
            startActivity(intent)
        }

        myDB = MyDatabase(this@MainActivity)
        val _id = ArrayList<String>()
        val task_title = ArrayList<String>()
        val task_author = ArrayList<String>()
        val task_number = ArrayList<String>()

        StoreData()
        customAdapter = CustomAdapter(
            this@MainActivity, this, _id, task_title, task_author,
            task_number
        )
        recyclerView.setAdapter(customAdapter)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            recreate()
        }
    }


    fun StoreData() {
         val cursor = myDB.readAllData()
         if (cursor?.count == 0) {
             Toast.makeText(this,"No Data.",Toast.LENGTH_SHORT).show()

         } else {
             if (cursor != null) {
                 while (cursor.moveToNext()) {
                     _id.add(cursor.getString(0))
                     task_title.add(cursor.getString(1))
                     task_author.add(cursor.getString(2))
                     task_number.add(cursor.getString(3))
                 }
             }
         }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all) {
            confirmDialog()
        }
        return super.onOptionsItemSelected(item)
    }
    fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to delete all Data?")
        builder.setPositiveButton("Yes") { dialogInterface, i ->
            val myDB = MyDatabase(this@MainActivity)
            myDB.deleteAllData()
            // Refresh Activity
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") { dialogInterface, i ->
            // Do nothing
        }
        builder.create().show()
    }


}
