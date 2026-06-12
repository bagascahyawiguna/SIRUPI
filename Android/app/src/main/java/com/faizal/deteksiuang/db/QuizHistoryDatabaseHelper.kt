package com.faizal.deteksiuang.db

import QuizHistoryModel
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Versi dinaikkan ke 2
class QuizHistoryDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "quiz_history.db", null, 2) {

    companion object {
        const val TABLE_NAME = "history"
        const val COL_ID = "id"
        const val COL_NAME = "name"        // Kolom Baru
        const val COL_CLASS = "class_name" // Kolom Baru
        const val COL_SCORE = "score"
        const val COL_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Query pembuatan tabel dengan kolom baru
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAME TEXT,
                $COL_CLASS TEXT,
                $COL_SCORE TEXT,
                $COL_DATE TEXT
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Fungsi Insert menerima Nama dan Kelas
    fun insertHistory(name: String, className: String, score: String, date: String) {
        val values = ContentValues().apply {
            put(COL_NAME, name)
            put(COL_CLASS, className)
            put(COL_SCORE, score)
            put(COL_DATE, date)
        }
        writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun getAllHistory(): MutableList<QuizHistoryModel> {
        val list = mutableListOf<QuizHistoryModel>()
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM $TABLE_NAME ORDER BY $COL_ID DESC",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                list.add(
                    QuizHistoryModel(
                        cursor.getInt(0),    // ID
                        cursor.getString(1), // Name
                        cursor.getString(2), // Class
                        cursor.getString(3), // Score
                        cursor.getString(4)  // Date
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun deleteHistory(id: Int) {
        writableDatabase.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))
    }
}