package com.example.iceenberg.Controllers

import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Service

class ServiceController (private val dbHelper: DatabaseHelper){


    fun getService(user_service: String):  MutableList<Service>{
        return dbHelper.getUserServices(user_service)
    }

    fun insertService(user_service:String ,equipment_service:Int,type_service:String,price: Int,finished : Int): Boolean {
        return dbHelper.insertServices(user_service,equipment_service,type_service,price,finished)
    }

    fun deleteService(id: Int) : Boolean {
        return dbHelper.deleteService(id)
    }

}