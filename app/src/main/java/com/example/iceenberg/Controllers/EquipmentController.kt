package com.example.iceenberg.Controllers

import android.content.Context
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Location

class EquipmentController(private val dbHelper: DatabaseHelper) {
    fun getLocation(context: Context) : MutableList<String> {
        return dbHelper.getNameLocations(context)
    }
}