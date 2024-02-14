package com.antonsmart.iceenberg.mantenimiento

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.antonsmart.iceenberg.Controllers.MaintenanceController
import com.antonsmart.iceenberg.Database.DatabaseHelper
import com.antonsmart.iceenberg.databinding.ActivityFormMantenimientoBinding

class FormMantenimientoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormMantenimientoBinding

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var maintenanceController: MaintenanceController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormMantenimientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFormMantenimientos)

        supportActionBar?.apply {
            title = "Nuevo mantenimiento"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarFormMantenimientos.setNavigationOnClickListener{
            onBackPressed()
        }


        //conexion a la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        maintenanceController = MaintenanceController(dbHelper)

        //Declarar las variables a usar
        var nameMaintenance: String;
        var costMaintenance: Double;

        //Tomar los datos del usuario
        binding.btnEdit.setOnClickListener {
            nameMaintenance = binding.nombreMantenimiento.text.toString()
            costMaintenance = binding.costoMantenimiento.text.toString().toDouble()

            //Insertar datos a la BD
            val exitoso = maintenanceController.insertMaintenance(nameMaintenance, costMaintenance);

            if (exitoso) {
                Toast.makeText(this, "Inserción exitosa", Toast.LENGTH_SHORT).show()

                val handler = Handler()
                val runnable = Runnable {
                    onBackPressed()
                }

                handler.postDelayed(runnable, 150) // 3 segundos de retraso
            } else {
                Toast.makeText(this, "Error al insertar datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}