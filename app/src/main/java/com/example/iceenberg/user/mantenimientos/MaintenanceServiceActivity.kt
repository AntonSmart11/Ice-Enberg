package com.example.iceenberg.user.mantenimientos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iceenberg.Controllers.MaintenanceController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityMaintenanceServiceBinding

class MaintenanceServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaintenanceServiceBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var maintenanceController: MaintenanceController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintenance_service)
        binding = ActivityMaintenanceServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarServiceMaintenance)

        binding.toolbarServiceMaintenance.setNavigationOnClickListener {
            onBackPressed()
        }


        //datos a insertar en la tabla de services
        //id_user
        //id_equipment
        //type
        //price
        //finish

    }
}