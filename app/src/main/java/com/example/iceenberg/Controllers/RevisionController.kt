package com.example.iceenberg.Controllers

import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Maintenance
import com.example.iceenberg.Objects.Revision

class RevisionController(private val dbHelper : DatabaseHelper){

    fun insertRevision(name: String, cost: Double): Boolean{
        return dbHelper.insertRevisions(name,cost)
    }

    fun getRevision() : MutableList<Revision>{
        return dbHelper.getRevisions()
    }

    fun updateRevision(id: Int, name: String,cost: Double) : Boolean{
        return dbHelper.updateRevision(id,name,cost)
    }

    fun deleteRevision(id: Int) : Boolean{
        return dbHelper.deleteRevision(id)
    }
}