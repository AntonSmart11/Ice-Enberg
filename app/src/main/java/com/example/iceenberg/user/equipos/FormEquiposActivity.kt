package com.example.iceenberg.user.equipos

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.iceenberg.Controllers.EquipmentController
import com.example.iceenberg.Controllers.LocationController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Equipments
import com.example.iceenberg.Objects.Location
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityFormEquiposBinding
import kotlin.math.log

class FormEquiposActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormEquiposBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var equipmentController: EquipmentController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_equipos)
        binding = ActivityFormEquiposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFormEquipos)

        binding.toolbarFormEquipos.setNavigationOnClickListener {
            onBackPressed()
        }

        var titleFormEquipment = getString(R.string.main_form_equipments_title)
        var nameEditText = ""
        var locationEditText = ""
        var directionEditText = ""
        var brandEditText = ""
        var modelEditText = ""
        var equipmentId = 0

        if (intent != null && intent.hasExtra("equipment")) {
            val equipment = intent.getSerializableExtra("equipment") as? Equipments
            titleFormEquipment = getString(R.string.main_form_equipments_title_edit)
            equipmentId = equipment?.id!!
            nameEditText = equipment.name
            locationEditText = equipment.location
            directionEditText = equipment.direction
            brandEditText = equipment.brand
            modelEditText = equipment.model

            binding.buttonsEdit.visibility = View.VISIBLE
        } else {
            binding.buttonsAdd.visibility = View.VISIBLE
        }

        supportActionBar?.apply {
            title = titleFormEquipment
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarFormEquipos.setNavigationOnClickListener {
            onBackPressed()
        }

        //conexion a la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        equipmentController = EquipmentController(dbHelper)

        //Declarar las variables a usar
        var userEquipment = 1
        var nameEquipment: String
        var positionLocationEquipment = 0
        var locationEquipment = ""
        var directionEquipment: String
        var brandEquipment: String
        var modelEquipment: String

        //Setear editText con el texto por defecto
        binding.nombreEquipo.setText(nameEditText)
        binding.marcaEquipo.setText(brandEditText)
        binding.modeloEquipo.setText(modelEditText)
        binding.direccionEquipo.setText(directionEditText)
        binding.localizacionEquipo.setSelection(positionLocationEquipment)

        val options = equipmentController.getLocation(this)

        // Método para obtener la posición de un nombre en el arreglo de datos del Spinner
        for ((indice, elemento) in options.withIndex()) {
            if (elemento == locationEditText) {
                positionLocationEquipment = indice
            }
        }

        // Crear un ArrayAdapter usando las opciones obtenidas
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Aplicar el adaptador al spinner
        binding.localizacionEquipo.adapter = adapter

        Log.d("position", locationEditText.toString())

        binding.localizacionEquipo.setSelection(positionLocationEquipment)

        // Manejar la selección del spinner
        binding.localizacionEquipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, pos: Int, id: Long) {
                // Acciones a realizar cuando se selecciona una opción
                val opcionSeleccionada = parent.getItemAtPosition(pos).toString()
                positionLocationEquipment = pos
                locationEquipment = opcionSeleccionada
            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {
                // Acciones a realizar cuando no se selecciona nada
            }
        }

        //Tomar los datos del usuario, botón agregar
        binding.btnAdd.setOnClickListener {
            nameEquipment = binding.nombreEquipo.text.toString()
            directionEquipment = binding.direccionEquipo.text.toString()
            brandEquipment = binding.marcaEquipo.text.toString()
            modelEquipment = binding.modeloEquipo.text.toString()

            if (nameEquipment.isNotEmpty() && directionEquipment.isNotEmpty() && brandEquipment.isNotEmpty() && modelEquipment.isNotEmpty()) {
                if (positionLocationEquipment != 0) {
                    val exitoso = equipmentController.insertEquipment(Equipments(0, 10, nameEquipment, locationEquipment, directionEquipment, brandEquipment, modelEquipment))

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
                } else {
                    Toast.makeText(this, getString(R.string.emptySpinner), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show()
            }
        }

        //Botón cancelar
        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }

        //Botón editar
        binding.btnEdit.setOnClickListener {
            nameEquipment = binding.nombreEquipo.text.toString()
            directionEquipment = binding.direccionEquipo.text.toString()
            brandEquipment = binding.marcaEquipo.text.toString()
            modelEquipment = binding.modeloEquipo.text.toString()

            if (nameEquipment.isNotEmpty() && directionEquipment.isNotEmpty() && brandEquipment.isNotEmpty() && modelEquipment.isNotEmpty()) {
                if (positionLocationEquipment != 0) {
                    val exitoso = equipmentController.updateEquipment(Equipments(equipmentId, 10, nameEquipment, locationEquipment, directionEquipment, brandEquipment, modelEquipment))

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
                } else {
                    Toast.makeText(this, getString(R.string.emptySpinner), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show()
            }
        }

        //Botón eliminar
        binding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val id = equipmentId

            builder.setTitle(getString(R.string.main_dialog_equipment_delete_title))
            builder.setMessage(getString(R.string.main_dialog_equipment_delete_message))

            builder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }

            builder.setPositiveButton(getString(R.string.delete)) { dialog, which ->
                val eliminacion = equipmentController.deleteEquipment(id)
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