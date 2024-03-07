package com.example.iceenberg.Controllers

import com.example.iceenberg.Database.DatabaseHelper


class UserController(private val dbHelper: DatabaseHelper) {

    fun insertUser(name: String, last: String, email: String, password: String, phone: String, type: Int): Boolean {
        return dbHelper.insertUser(name, last, email, password, phone, type)
    }
    fun updateUser(id: Int, name: String, last: String, email: String, password: String, phone: String) : Boolean {
        return dbHelper.updateUser(id, name, last, email, password, phone)
    }
    fun deleteUser(id: Int): Boolean {
        return dbHelper.deleteUser(id)
    }

}