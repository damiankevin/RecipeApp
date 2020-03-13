package com.android.recipeapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, TABLE_NAME, null, 1) {


    val data: Cursor
        get() {
            val db = this.writableDatabase
            val query = "SELECT * FROM $TABLE_NAME"
            return db.rawQuery(query, null)
        }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable =
            "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL2 + " TEXT,"+
                    COL3 + " TEXT,"+
                    COL4 + " TEXT,"+
                    COL5 + " TEXT"+
                    ")"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL("DROP  TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addData(item: String, recipe: String, step: String, url: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL2, item)
        contentValues.put(COL3, recipe)
        contentValues.put(COL4, step)
        contentValues.put(COL5, url)


        Log.d(TAG, "addData: Adding $item to $TABLE_NAME")

        val result = db.insert(TABLE_NAME, null, contentValues)

        return result != -1L
    }


    fun getItemID(name: String): Cursor {
        val db = this.writableDatabase
        val query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'"
        return db.rawQuery(query, null)
    }


    fun update(newName: String, id: Int, oldName: String,
                   newRecipe : String, oldRecipe : String,
                   newStep : String, oldStep : String,
                   newUrl: String, oldUrl : String
                   ) {
        val db = this.writableDatabase
        val query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'"
        val query2 = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + newRecipe + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL3 + " = '" + oldRecipe + "'"
        val query3 = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + newStep + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL4 + " = '" + oldStep + "'"
        val query4 = "UPDATE " + TABLE_NAME + " SET " + COL5 +
                " = '" + newUrl + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL5 + " = '" + oldUrl + "'"

        Log.d(TAG, "updateName: query: $query")
        Log.d(TAG, "updateName: Setting name to $newName")
        db.execSQL(query)
        db.execSQL(query2)
        db.execSQL(query3)
        db.execSQL(query4)
    }


    fun deleteId(id: Int) {
        val db = this.writableDatabase
        val query = ("DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'")
        Log.d(TAG, "deleteName: query: $query")
        db.execSQL(query)
    }

    companion object {
        private val TAG = "DatabaseHelper"
        private val TABLE_NAME = "recipe_table"
        private val COL1 = "ID"
        private val COL2 = "name"
        private val COL3 = "ingredient"
        private val COL4 = "step"
        private val COL5 = "picture"

    }

}





