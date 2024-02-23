package com.example.iceenberg.Controllers

import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Location
import com.example.iceenberg.Objects.Maintenance

class MaintenanceController(private val dbHelper: DatabaseHelper){

    fun insertMaintenance(name: String, cost: Double): Boolean {
       return dbHelper.insertMaintenance(name, cost)
    }

    fun getMaintenance() : MutableList<Maintenance>{
        return dbHelper.getMaintenances()
    }

    fun updateMaintenance(id:Int, name: String, cost: Double) : Boolean{
        return dbHelper.updateMaintenance(id,name,cost)
    }

    fun deleteMaintenance(id: Int) : Boolean {
        return dbHelper.deleteMaintenance(id)
    }



}