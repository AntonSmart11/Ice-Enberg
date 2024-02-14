package com.antonsmart.iceenberg.Controllers

import com.antonsmart.iceenberg.Database.DatabaseHelper

class MaintenanceController(private val dbHelper: DatabaseHelper){

    fun insertMaintenance(name: String, cost: Double): Boolean {
       return dbHelper.insertMaintenance(name, cost)
    }
}