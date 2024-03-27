package com.example.iceenberg.user.Servicios

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.iceenberg.Controllers.EquipmentController
import com.example.iceenberg.Controllers.ServiceController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Service
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityFormServiceBinding

class FormServiceActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var binding: ActivityFormServiceBinding
    private lateinit var equipmentController: EquipmentController
    private lateinit var serviceController: ServiceController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFormServices)

        supportActionBar?.apply {
            title = getString(R.string.main_form_maintenance_title)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding.toolbarFormServices.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }

        //conexión a la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        equipmentController = EquipmentController(dbHelper)
        serviceController = ServiceController(dbHelper)

        //recibir los datos
        val intent = intent
        val service = intent.getSerializableExtra("service") as? Service

        //Variables a usar
        val serviceTitle = service?.type_service
        val costService = service?.price
        val statusService = service?.finished
        val equipmentID = service?.equipment_service
        val equiment = equipmentID?.let { equipmentController.getSpecificEquipment(it.toInt()) }
        val serviceId = service?.id

        var serviceNameTextView = binding.nombreServicio
        var costServiceTextView = binding.precioServicio
        var statuTextView = binding.estadoServicio
        var equipmentTextView = binding.nombreEquipo

        //Asignar los datos a los textView

        serviceNameTextView.text = getString(R.string.main_form_service_title) + ": $serviceTitle"
        costServiceTextView.text = getString(R.string.total) + " $costService"
        if (equiment != null) {
            equipmentTextView.text = getString(R.string.main_form_service_equipment_name) + ": ${equiment.name}"
        }

        if (statusService == 1){
            statuTextView.text = getString(R.string.main_form_service_status) + ": " + getString(R.string.main_form_service_status_finish)
        }else{
            statuTextView.text = getString(R.string.main_form_service_status) + ": " + getString(R.string.main_form_service_status_process)
        }



        //Eliminar datos de la BD
        binding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val id : Int = serviceId?.toInt() ?: 0

            builder.setTitle(getString(R.string.main_dialog_service_delete_title))
            builder.setMessage(getString(R.string.main_dialog_service_delete_message))

            builder.setNegativeButton(getString(R.string.cancel)){dialog, which ->
                dialog.dismiss()
            }


            builder.setPositiveButton(getString(R.string.delete)){dialog, which ->
                val eliminacion = serviceController.deleteService(id)
                if(eliminacion) {
                    dialog.dismiss()
                    Toast.makeText(this, getString(R.string.successDelete), Toast.LENGTH_SHORT).show()
                    onBackPressed()
                } else {
                    dialog.dismiss()
                    Toast.makeText(this, getString(R.string.errorDelete), Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }

            }

            // Crear y mostrar el diálogo
            val dialog = builder.create()
            dialog.show()

        }
    }
}