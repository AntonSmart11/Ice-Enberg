package com.example.iceenberg.Controllers

import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Installation

class InstallationController(private val dbHelper: DatabaseHelper) {

    fun insertInstallation(name: String, cost: Double): Boolean {
        return dbHelper.insertInstallation(name, cost)
    }

    fun getInstallation() : MutableList<Installation> {
        return dbHelper.getInstallation()
    }
    fun updateInstallation(id: Int, name: String, cost: Double) : Boolean {
        return dbHelper.updateInstallation(id, name, cost)
    }

    fun deleteInstallation(id: Int): Boolean {
        return dbHelper.deleteInstallation(id)
    }
}