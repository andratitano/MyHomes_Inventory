package com.andra.myhomes_inventory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            this.supportActionBar?.hide()
        } catch (e: NullPointerException) {}


        fabAddInventory.setOnClickListener {
            val intentAddInventory = Intent(this, AddInventory::class.java)
            startActivity(intentAddInventory)
        }

    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }


    fun fetchData(){
        val db = DatabaseHandler(this)
        val listInventory = arrayListOf<Inventory>()
        listInventory.addAll(db.readAllInventory())
        val adapter = InventoryAdapter(listInventory)
        recycleViewContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recycleViewContainer.adapter = adapter
    }
}