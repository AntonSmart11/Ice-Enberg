package com.antonsmart.iceenberg.mantenimiento

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.antonsmart.iceenberg.Controllers.MaintenanceController
import com.antonsmart.iceenberg.Database.DatabaseHelper
import com.antonsmart.iceenberg.R
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
            title = getString(R.string.main_form_location_title)
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
        var costMaintenance: String;
        var costMaintenanceNum: Double;

        //Tomar los datos del usuario
        binding.btnEdit.setOnClickListener {
            nameMaintenance = binding.nombreMantenimiento.text.toString()
            costMaintenance = binding.costoMantenimiento.text.toString()

            if (nameMaintenance.isNotEmpty() && costMaintenance.isNotEmpty()) {
                costMaintenanceNum = costMaintenance.toDouble()

                //Insertar datos a la BD
                val exitoso = maintenanceController.insertMaintenance(nameMaintenance, costMaintenanceNum);

                if (exitoso) {
                    Toast.makeText(this, getString(R.string.successInsert), Toast.LENGTH_SHORT).show()

                    val handler = Handler()
                    val runnable = Runnable {
                        onBackPressed()
                    }

                    handler.postDelayed(runnable, 150) // 3 segundos de retraso
                } else {
                    Toast.makeText(this, getString(R.string.errorInsert), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show()
            }


        }
    }
}