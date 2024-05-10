package com.example.taskflow
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "TaskFlow.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "my_Task"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "task_title"
        private const val COLUMN_AUTHOR = "task_author"
        private const val COLUMN_PAGES = "task_pages"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let {
            val query = "CREATE TABLE $TABLE_NAME " +
                    "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TITLE TEXT, " +
                    "$COLUMN_AUTHOR TEXT, " +
                    "$COLUMN_PAGES INTEGER)"
            it.execSQL(query)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let {
            it.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(it)
        }
    }
}
