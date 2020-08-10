package com.andra.myhomes_inventory

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler (context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        private const val DB_NAME = "inventoryDB"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "InventoryTable"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_TOTAL = "total"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val queryCreateTable = ("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_TOTAL INTEGER)")
        db?.execSQL(queryCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val queryDeleteTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(queryDeleteTable)
        onCreate(db)
    }

    fun addInventory(inventory: Inventory): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(COLUMN_NAME, inventory.name)
        contentValues.put(COLUMN_TOTAL, inventory.total)

        val savedSuccess = db.insert(TABLE_NAME,null, contentValues)

        db.close()

        return savedSuccess
    }

    fun readAllInventory(): ArrayList<Inventory>{
        val inventoryList = ArrayList<Inventory>()

        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val db = this.readableDatabase

        var cursor: Cursor? = null

        cursor = db.rawQuery(selectQuery, null, null)

        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val total = cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL))

                val objInventory = Inventory(id,name,total)

                inventoryList.add(objInventory)
            } while (cursor.moveToNext())
        }
        return inventoryList
    }

    fun updateInventory(inventory: Inventory): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COLUMN_ID, inventory.id)
        contentValues.put(COLUMN_NAME, inventory.name)
        contentValues.put(COLUMN_TOTAL, inventory.total)

        val updateSuccess = db.update(
            TABLE_NAME,contentValues,
            "$COLUMN_ID = ${inventory.id}",
            null)

        db.close()

        return updateSuccess
    }

    fun deleteInventory(inventory: Inventory): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COLUMN_ID, inventory.id)

        val deleteSuccess = db.delete(TABLE_NAME, "$COLUMN_ID = ${inventory.id}",null)

        db.close()

        return deleteSuccess
    }
}