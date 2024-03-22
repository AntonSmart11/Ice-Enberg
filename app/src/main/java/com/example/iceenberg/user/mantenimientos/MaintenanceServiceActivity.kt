package com.example.iceenberg.user.mantenimientos

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.iceenberg.Controllers.EquipmentController
import com.example.iceenberg.Controllers.LocationController
import com.example.iceenberg.Controllers.MaintenanceController
import com.example.iceenberg.Controllers.ServiceController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Global
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityMaintenanceServiceBinding

class MaintenanceServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMaintenanceServiceBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var maintenanceController: MaintenanceController
    private lateinit var equipmentController: EquipmentController
    private lateinit var lacationController: LocationController
    private lateinit var serviceController: ServiceController


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
        serviceController = ServiceController(dbHelper)


        //variables a usar

        val optionsMantenimientos = maintenanceController.getMaintenance()
        val optionsMantenimientosWithHint = mutableListOf("Elija una opción")
        optionsMantenimientosWithHint.addAll(optionsMantenimientos.map { it.name })



        val optionsEquipos = equipmentController.getEquipment()
        val optionsEquiposWithHint = mutableListOf("Elija una opción")
        optionsEquiposWithHint.addAll(optionsEquipos.map { it.name })

        var equipment_id = 0
        var maintenance = ""
        val user_service = Global.usuarioCorreo
        var positionMaintenance = 0
        var positionEquipment = 0
        var priceService = 0;
        var priceMaintenance = 0.0
        var locationPercentage = 0
        var textViewPriceService = binding.precioMantenimiento



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
                    positionMaintenance = position;
                    //Esto es para cuando seleccione una opción diferente a la primera opción, la cual es el "hint"
                    maintenance =  optionsMantenimientos[position - 1].name
                    priceMaintenance = optionsMantenimientos[position - 1].cost


                    if(positionMaintenance > 0 && positionEquipment > 0 ){
                        priceService = priceMaintenance.toInt()/100 * locationPercentage.toInt() + priceMaintenance.toInt()
                        textViewPriceService.text = getString(R.string.total) + " $priceService"
                    }
                }else {
                    textViewPriceService.text = getString(R.string.total)
                    positionMaintenance = 0
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
                    positionEquipment = position;
                    //Esto es para cuando seleccione una opción diferente a la primera opción, la cual es el "hint"
                    equipment_id =  optionsEquipos[position - 1].id

                    //obtener la location para tomar su porcentaje
                    locationPercentage = lacationController.getSpecificLocation(optionsEquipos[position - 1].location.toString())?.percentage?: 0


                    if(positionMaintenance > 0 && positionEquipment > 0 ){
                        priceService = priceMaintenance.toInt()/100 * locationPercentage.toInt() + priceMaintenance.toInt()
                        textViewPriceService.text = getString(R.string.total) + " $priceService"
                    }
                }else {
                    textViewPriceService.text = getString(R.string.total)
                    positionEquipment = 0
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar cuando no se selecciona nada
            }
        }

        binding.btnAdd.setOnClickListener {
            if(positionMaintenance > 0 && positionEquipment > 0 ){
                //obtener el precio del servicio
                priceService = priceMaintenance.toInt()/100 * locationPercentage.toInt() + priceMaintenance.toInt()

                val exitoso = serviceController.insertService(user_service,equipment_id,maintenance,priceService,0)
                if (exitoso) {
                    Toast.makeText(this, getString(R.string.successInsert), Toast.LENGTH_SHORT).show()

                    val handler = Handler()
                    val runnable = Runnable {
                        onBackPressed()
                    }

                    handler.postDelayed(runnable, 150)
                } else {
                    Toast.makeText(this, getString(R.string.errorInsert), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }
    }
}