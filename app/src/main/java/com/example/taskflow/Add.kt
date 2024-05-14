package com.example.taskflow

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Add : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title_input = findViewById<EditText>(R.id.title_input)
        val author_input = findViewById<EditText>(R.id.author_input)
       val number_input = findViewById<EditText>(R.id.number_input)
        val add_button = findViewById<Button>(R.id.add_button)

        add_button.setOnClickListener {
            val myDB = MyDatabase(this@Add)
            myDB.addTask(
                this@Add,
                title_input.text.toString().trim(),
                author_input.text.toString().trim(),
                number_input.text.toString().trim().toInt()
            )
        }

    }
}