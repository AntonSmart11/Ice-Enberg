package com.antonsmart.iceenberg.Controllers

import com.antonsmart.iceenberg.Database.DatabaseHelper
import com.antonsmart.iceenberg.Objects.Location

class LocationController(private val dbHelper: DatabaseHelper) {

    fun insertLocation(name: String, percentage: Int): Boolean {
        return dbHelper.insertLocations(name, percentage)
    }

    fun getLocation() : List<Location> {
        return dbHelper.getLocations()
    }
}