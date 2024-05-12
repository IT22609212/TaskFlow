package com.example.taskflow
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast




class MyDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "TaskFlow.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "my_Task"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_TITLE = "task_title"
        private const val COLUMN_AUTHOR = "task_author"
        private const val COLUMN_NUMBER = "task_number"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let {
            val query = "CREATE TABLE $TABLE_NAME " +
                    "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TITLE TEXT, " +
                    "$COLUMN_AUTHOR TEXT, " +
                    "$COLUMN_NUMBER INTEGER)"
            it.execSQL(query)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let {
            it.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(it)
        }
    }

    fun addTask(context: Context, title: String, author: String, number: Int) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_TITLE, title)
        cv.put(COLUMN_AUTHOR, author)
        cv.put(COLUMN_NUMBER, number)
        val result = db.insert(TABLE_NAME, null, cv)
        if (result == -1L) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    fun readAllData(): Cursor? {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    fun updateData(context: Context, rowId: String, title: String, author: String, number: String) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_TITLE, title)
        cv.put(COLUMN_AUTHOR, author)
        cv.put(COLUMN_NUMBER, number)

        val result = db.update(TABLE_NAME, cv, "_id=?", arrayOf(rowId))
        if (result.toLong() == -1L) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteOneRow(context: Context, rowId: String) {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "_id=?", arrayOf(rowId)).toLong()
        if (result == -1L) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteAllData() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
    }

}
