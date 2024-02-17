package com.antonsmart.iceenberg.Controllers

import com.antonsmart.iceenberg.Database.DatabaseHelper
import com.antonsmart.iceenberg.Objects.Installation

class InstallationController(private val dbHelper: DatabaseHelper) {

    fun insertInstallation(name: String, cost: Double): Boolean {
        return dbHelper.insertInstallation(name, cost)
    }

    fun getInstallation() : List<Installation> {
        return dbHelper.getInstallation()
    }
}