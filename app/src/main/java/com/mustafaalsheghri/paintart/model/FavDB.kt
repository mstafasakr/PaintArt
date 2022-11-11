package com.mustafaalsheghri.paintart.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class FavDB(var context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null, 1) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL("create table $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"imageUrl TEXT , favStatus TEXT)")
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {}

    // create empty table
    fun insertEmpty() {
        val db = this.writableDatabase
        val cv = ContentValues()
        // enter your value
        for (x in 1..10) {
            cv.put(KEY_ID, x)
            cv.put(FAVORITE_STATUS, "0")
            db.insert(TABLE_NAME, null, cv)
        }
    }

    // insert data into database
    fun insertIntoTheDatabase( item_image: String, fav_status: String) {
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(ITEM_IMAGE, item_image)
     //   cv.put(KEY_ID, id)
        cv.put(FAVORITE_STATUS, fav_status)
        db.insert(TABLE_NAME, null, cv)
    }

    // read all data
    fun read_all_data(id: String): Cursor {
        val db = this.readableDatabase
        val sql = "select * from $TABLE_NAME where $KEY_ID=$id"
        return db.rawQuery(sql, null, null)
    }
    val allData : Cursor
        get(){
            val db = this.writableDatabase
            val res = db.rawQuery("Select * from $TABLE_NAME",null)
            return  res
        }
    // remove line from database
    fun remove_fav(id: String) {
        val db = this.writableDatabase
        val sql = "UPDATE $TABLE_NAME SET  $FAVORITE_STATUS ='0' WHERE $KEY_ID=$id"
        db.execSQL(sql)
        Log.d("remove", id)
    }

    // select all favorite list
    fun select_all_favorite_list(): Cursor {
        val db = this.readableDatabase
        val sql = "SELECT * FROM $TABLE_NAME WHERE $FAVORITE_STATUS ='1'"
        return db.rawQuery(sql, null, null)
    }

    companion object {
        private const val DB_VERSION = 1
        private const val DATABASE_NAME = "CoffeeDB"
        private const val TABLE_NAME = "favoriteTable"
        @JvmField
        var KEY_ID = "id"
        @JvmField
        var ITEM_IMAGE = "imageUrl"
        @JvmField
        var FAVORITE_STATUS = "favStatus"

    }


}