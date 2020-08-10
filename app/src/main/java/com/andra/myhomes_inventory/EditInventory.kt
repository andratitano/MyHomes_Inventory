package com.andra.myhomes_inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_inventory.*

class EditInventory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_inventory)




        val dbHandler = DatabaseHandler(this)

        val inventory = intent.getParcelableExtra<Inventory>("inventory")

        if (inventory != null) {
            supportActionBar?.title = "Ubah data ${inventory.name}"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        inventoryNameEdit.setText(inventory?.name)
        inventoryTotalEdit.setText(inventory?.total.toString())

        btnEdit.setOnClickListener {
            inventory?.name = inventoryNameEdit.text.toString()
            inventory?.total = inventoryTotalEdit.text.toString().toInt()

            inventory?.let {
                if (dbHandler.updateInventory(it)!=0){
                    Toast.makeText(this,"Data barang berhasil dirubah", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this,"Gagal merubah data barang",Toast.LENGTH_LONG).show()
                }
            }
        }

        fun onSupportNavigateUp(): Boolean {
            finish()
            return super.onSupportNavigateUp()
        }
    }
}