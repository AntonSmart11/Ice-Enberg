package com.example.iceenberg.user.mantenimientos

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.iceenberg.Controllers.EquipmentController
import com.example.iceenberg.Controllers.LocationController
import com.example.iceenberg.Controllers.MaintenanceController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityMaintenanceServiceBinding

class MaintenanceServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaintenanceServiceBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var maintenanceController: MaintenanceController
    private lateinit var equipmentController: EquipmentController
    private lateinit var lacationController: LocationController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintenance_service)
        binding = ActivityMaintenanceServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarServiceMaintenance)

        binding.toolbarServiceMaintenance.setNavigationOnClickListener {
            onBackPressed()
        }
        //conexion a la bd y llamado del controlador

        dbHelper = DatabaseHelper(this)
        maintenanceController = MaintenanceController(dbHelper)
        equipmentController = EquipmentController(dbHelper)
        lacationController = LocationController(dbHelper)


        //variables a usar

        val optionsMantenimientos = maintenanceController.getMaintenance()
        val optionsMantenimientosWithHint = mutableListOf("Elija una opción")
        optionsMantenimientosWithHint.addAll(optionsMantenimientos.map { it.name })
        var maintenance = ""

        val optionsEquipos = equipmentController.getEquipment()
        val optionsEquiposWithHint = mutableListOf("Elija una opción")
        optionsEquiposWithHint.addAll(optionsEquipos.map { it.name })
        var equipment = ""


        //Variables a usar
        //datos a insertar en la tabla de services
        //id_user
        //id_equipment
        //type
        //price
        //finish



        //Spinner de los mantenimientos
        //Crear un ArrayAdapter usando las opciones obtenidos
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,optionsMantenimientosWithHint)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Aplicar el adaptador al spinner
        binding.tipoMantenimiento.adapter = adapter

        //Manejar la selección del spinner
        binding.tipoMantenimiento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Acciones a realizar cuando se selecciona una opción
                if (position > 0) {
                    //Esto es para cuando seleccione una opción diferente a la primera opción, la cual es el "hint"
                    maintenance =  optionsMantenimientos[position - 1].toString()
                    Toast.makeText(
                        this@MaintenanceServiceActivity,
                        "Mantenimiento seleccionado $maintenance",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar cuando no se selecciona nada
            }
        }


        //Spinner con los equipos
        //Crear un arrayAdapter usando las opciones obtenidas
        val adapterEquipo = ArrayAdapter(this,android.R.layout.simple_spinner_item,optionsEquiposWithHint)
        adapterEquipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Aplicar adaptador al spinner
        binding.equipoMantenimiento.adapter = adapterEquipo

        //Manejar la selección del spinner
        binding.equipoMantenimiento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Acciones a realizar cuando se selecciona una opción
                if (position > 0) {
                    //Esto es para cuando seleccione una opción diferente a la primera opción, la cual es el "hint"
                    equipment =  optionsEquipos[position - 1].toString()
                    var location = lacationController.getSpecificLocation(optionsEquipos[position - 1].location.toString())


                    Toast.makeText(
                        this@MaintenanceServiceActivity,
                        "Equipo seleccionado $location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar cuando no se selecciona nada
            }
        }
    }
}