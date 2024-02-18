package com.example.iceenberg.Controllers

import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Location

class LocationController(private val dbHelper: DatabaseHelper) {

    fun insertLocation(name: String, percentage: Int): Boolean {
        return dbHelper.insertLocations(name, percentage)
    }

    fun getLocation() : List<Location> {
        return dbHelper.getLocations()
    }
}