package com.example.iceenberg.Controllers

import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Installation

class InstallationController(private val dbHelper: DatabaseHelper) {

    fun insertInstallation(name: String, cost: Double): Boolean {
        return dbHelper.insertInstallation(name, cost)
    }

    fun getInstallation() : List<Installation> {
        return dbHelper.getInstallation()
    }

    fun deleteInstallation(id: Int): Boolean {
        return dbHelper.deleteInstallation(id)
    }
}