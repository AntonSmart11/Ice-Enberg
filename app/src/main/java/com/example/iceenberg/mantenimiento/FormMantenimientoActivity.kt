package com.example.iceenberg.mantenimiento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.example.iceenberg.Controllers.MaintenanceController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Location
import com.example.iceenberg.Objects.Maintenance
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityFormMantenimientoBinding

class FormMantenimientoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormMantenimientoBinding

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var maintenanceController: MaintenanceController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormMantenimientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFormMantenimientos)


        //Se confirma si llega un dato a la activity
        val intent = intent
        var titleFormMaintenance = getString(R.string.main_form_maintenance_title)
        var nameEditText = ""
        var costEditText = 0
        var maintenanceID = 0

        if (intent != null && intent.hasExtra("maintenance")) {
            Toast.makeText(this, "Llego", Toast.LENGTH_SHORT).show()
            val maintenance = intent.getSerializableExtra("maintenance") as? Maintenance
            titleFormMaintenance = getString(R.string.main_form_maintenance_title_edit)
            val maintenanceId = maintenance?.id!!
            maintenanceID = maintenanceId
            nameEditText = maintenance.name
            costEditText = maintenance.cost.toInt()

            binding.buttonsEdit.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, "No llego", Toast.LENGTH_SHORT).show()
            binding.buttonsAdd.visibility = View.VISIBLE
        }


        supportActionBar?.apply {
            title = getString(R.string.main_form_maintenance_title)
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


        //Setear editText con el texto por defecto
        binding.nombreMantenimiento.setText(nameEditText)
        binding.costoMantenimiento.setText(costEditText.toString())

        //Tomar los datos del usuario
        binding.btnaAdd.setOnClickListener {
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

        //Editar datos en la BD
        binding.btnEdit.setOnClickListener {

            nameMaintenance = binding.nombreMantenimiento.text.toString()
            costMaintenance = binding.costoMantenimiento.text.toString()

            if (nameMaintenance.isNotEmpty() && costMaintenance.isNotEmpty()) {
                costMaintenanceNum = costMaintenance.toDouble()

                //Insertar datos a la BD
                val exitoso = maintenanceController.updateMaintenance(maintenanceID,nameMaintenance,costMaintenanceNum);

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