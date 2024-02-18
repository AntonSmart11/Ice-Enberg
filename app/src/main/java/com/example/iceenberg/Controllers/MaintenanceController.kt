package com.example.iceenberg.Controllers

import com.example.iceenberg.Database.DatabaseHelper

class MaintenanceController(private val dbHelper: DatabaseHelper){

    fun insertMaintenance(name: String, cost: Double): Boolean {
       return dbHelper.insertMaintenance(name, cost)
    }
}