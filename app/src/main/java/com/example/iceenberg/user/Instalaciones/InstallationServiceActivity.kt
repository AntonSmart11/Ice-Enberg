package com.example.iceenberg.user.Instalaciones

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.iceenberg.Controllers.EquipmentController
import com.example.iceenberg.Controllers.InstallationController
import com.example.iceenberg.Controllers.LocationController
import com.example.iceenberg.Controllers.ServiceController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Global
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityInstallationServiceBinding

class InstallationServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInstallationServiceBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var installationController: InstallationController
    private lateinit var equipmentController: EquipmentController
    private lateinit var locationController: LocationController
    private lateinit var serviceController: ServiceController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_installation_service)
        binding = ActivityInstallationServiceBinding.inflate((layoutInflater))
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarServiceInstalation)

        binding.toolbarServiceInstalation.setNavigationOnClickListener {
            onBackPressed()
        }
        //conexión a la bd y llamado de los controladores
        dbHelper = DatabaseHelper(this)
        installationController = InstallationController(dbHelper)
        equipmentController = EquipmentController(dbHelper)
        locationController = LocationController(dbHelper)
        serviceController = ServiceController(dbHelper)


        //Variables a usar

        val optionsInstalaciones = installationController.getInstallation()
        val optionsInstalacionesWithHint = mutableListOf("Elija una opción")
        optionsInstalacionesWithHint.addAll(optionsInstalaciones.map { it.name })


        val optionsEquipos = equipmentController.getEquipment()
        val optionsEquiposWithHint = mutableListOf("Elija una opción")
        optionsEquiposWithHint.addAll(optionsEquipos.map { it.name })


        var equipment_id = 0
        var installation = ""
        val user_service = Global.usuarioCorreo
        var positionInstallation = 0
        var positionEquipment = 0
        var priceService = 0
        var priceInstallation = 0.0
        var locationPercentage = 0
        var textViewPriceService = binding.precioInstalacion


        //Spinner de las instalaciones
        //Crear un ArrayAdapter usando las opciones obtenidas
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,optionsInstalacionesWithHint)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Aplicar el adaptador al spinner
        binding.tipoInstalacion.adapter = adapter

        //Manejar la seleccion del spinner
        binding.tipoInstalacion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //Acciones a hacer cuando se selecciona una opción
                if (position > 0){
                    positionInstallation = position;
                    //Esto es para cuando seleccione una opción diferente a la primera opción la cual es el "hint"
                    installation = optionsInstalaciones[position - 1].name
                    priceInstallation = optionsInstalaciones[position - 1].cost

                    if(positionInstallation > 0 && positionEquipment > 0){
                        priceService = priceInstallation.toInt()/100 * locationPercentage.toInt() + priceInstallation.toInt()
                        textViewPriceService.text = getString(R.string.total) + " $priceService"

                    }
                }else{
                    textViewPriceService.text = getString(R.string.total)
                    positionInstallation = 0
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar cuando no se selecciona nada
            }
        }

        //Spinner con los equipos
        //Crear un ArrayAdapter usando las opciones obtenidas
        val adapterEquipo = ArrayAdapter(this,android.R.layout.simple_spinner_item,optionsEquiposWithHint)
        adapterEquipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Aplicar adaptador al spinner
        binding.equipoInstalacion.adapter = adapterEquipo

        //Manejar la selección del spinner
        binding.equipoInstalacion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //Acciones a hacer cuando se selecciona una opción
                if (position > 0){
                    positionEquipment = position
                    //Esto es para cuando seleccione una opción diferente a la primera opción, la cual es el hint
                    equipment_id = optionsEquipos[position - 1].id

                    //obtener la location para tomar su porcentaje
                    locationPercentage = locationController.getSpecificLocation(optionsEquipos[position - 1].location.toString())?.percentage?: 0

                    if(positionInstallation > 0 && positionEquipment > 0){
                        priceService = priceInstallation.toInt()/100 * locationPercentage.toInt() + priceInstallation.toInt()
                        textViewPriceService.text = getString(R.string.total) + " $priceService"
                    }
                }else{
                    textViewPriceService.text = getString(R.string.total)
                    positionEquipment = 0
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar cuando no se selecciona nada
            }
        }

        binding.btnAdd.setOnClickListener {
            if(positionInstallation > 0 && positionEquipment > 0){
                //obtener el precio del servicio
                priceService = priceInstallation.toInt()/100 * locationPercentage.toInt() + priceInstallation.toInt()

                val exitoso = serviceController.insertService(user_service,equipment_id,installation,priceService,0)
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