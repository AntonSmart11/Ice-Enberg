package com.example.iceenberg.Controllers

import android.content.Context
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Equipments
import com.example.iceenberg.Objects.Location

class EquipmentController(private val dbHelper: DatabaseHelper) {
    fun getLocation(context: Context) : MutableList<String> {
        return dbHelper.getNameLocations(context)
    }

    fun insertEquipment(equipments: Equipments): Boolean {
        return dbHelper.insertEquipments(equipments)
    }

    fun getEquipment(): MutableList<Equipments> {
        return dbHelper.getEquipments()
    }

    fun updateEquipment(equipments: Equipments) : Boolean {
        return dbHelper.updateEquipment(equipments)
    }

    fun deleteEquipment(id: Int) : Boolean {
        return dbHelper.deleteEquipment(id)
    }
}