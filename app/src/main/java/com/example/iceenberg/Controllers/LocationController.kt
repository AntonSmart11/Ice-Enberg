package com.example.iceenberg.Controllers

import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Location

class LocationController(private val dbHelper: DatabaseHelper) {

    fun insertLocation(name: String, percentage: Int): Boolean {
        return dbHelper.insertLocations(name, percentage)
    }

    fun getLocation() : MutableList<Location> {
        return dbHelper.getLocations()
    }

    fun updateLocation(id: Int, name: String, percentage: Int) : Boolean {
        return dbHelper.updateLocation(id, name, percentage)
    }

    fun deleteInstallation(id: Int) : Boolean {
        return dbHelper.deleteLocation(id)
    }

    fun getSpecificLocation(locationName : String): Location? {
        return dbHelper.getSpecificLocation(locationName)
    }
}