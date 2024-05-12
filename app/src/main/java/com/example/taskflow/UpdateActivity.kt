package com.example.taskflow

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity : AppCompatActivity() {

    private lateinit var title_input: EditText
    private lateinit var author_input: EditText
    private lateinit var number_input: EditText
    private lateinit var update_button: Button
    private lateinit var delete_button: Button

    private var id = ""
    private var title = ""
    private var author = ""
    private var number = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        title_input = findViewById(R.id.title_input2)
        author_input = findViewById(R.id.author_input2)
        number_input = findViewById(R.id.number_input2)
        update_button = findViewById(R.id.update_button)
       // delete_button = findViewById(R.id.delete_button)


        getAndSetIntentData()

        supportActionBar?.title = title

        update_button.setOnClickListener {

            val myDB = MyDatabase(this@UpdateActivity)
            val title = title_input.text.toString().trim()
            val author = author_input.text.toString().trim()
            val number = number_input.text.toString().trim()

            myDB.updateData(this@UpdateActivity,id, title, author, number)
        }


        delete_button.setOnClickListener {
            confirmDialog()
        }
    }

    private fun getAndSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("title") &&
            intent.hasExtra("author") && intent.hasExtra("number")
        ) {
            //Getting Data from Intent
            id = intent.getStringExtra("id").toString()
            title = intent.getStringExtra("title").toString()
            author = intent.getStringExtra("author").toString()
            number = intent.getStringExtra("number").toString()

            //Setting Intent Data
            title_input.setText(title)
            author_input.setText(author)
            number_input.setText(number)
            Log.d("stev", "$title $author $number")
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmDialog() {
        // Implement your confirmation dialog here
    }
}
