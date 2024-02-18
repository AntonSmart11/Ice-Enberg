package com.example.iceenberg.localizacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.example.iceenberg.Controllers.LocationController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Location
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityFormLocalizacionBinding

class FormLocalizacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormLocalizacionBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var locationController: LocationController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_localizacion)
        binding = ActivityFormLocalizacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFormLocalizaciones)

        //Se confirma si llega un dato a la activity
        val intent = intent
        var titleFormLocation = getString(R.string.main_form_location_title)
        var nameEditText = ""
        var percentageEditText = ""

        if (intent != null && intent.hasExtra("location")) {
            Toast.makeText(this, "Llego", Toast.LENGTH_SHORT).show()
            val location = intent.getSerializableExtra("location") as? Location
            titleFormLocation = getString(R.string.main_form_location_title_edit)
            val locationId = location?.id!!
            nameEditText = location.name
            percentageEditText = location.percentage.toString()

            binding.buttonsEdit.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, "No llego", Toast.LENGTH_SHORT).show()
            binding.buttonsAdd.visibility = View.VISIBLE
        }

        supportActionBar?.apply {
            title = titleFormLocation
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarFormLocalizaciones.setNavigationOnClickListener {
            onBackPressed()
        }


        //conexion a la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        locationController = LocationController(dbHelper)

        //Declarar las variables a usar
        var nameLocation: String;
        var percentageLocation: String
        var percentageLocationNum: Int;

        //Setear editText con el texto por defecto
        binding.nombreLocalizacion.setText(nameEditText)
        binding.porcentajeLocalizacion.setText(percentageEditText)

        //Tomar los datos del usuario, botón agregar
        binding.btnAdd.setOnClickListener {
            nameLocation = binding.nombreLocalizacion.text.toString()
            percentageLocation = binding.porcentajeLocalizacion.text.toString()

            if (nameLocation.isNotEmpty() && percentageLocation.isNotEmpty()) {
                percentageLocationNum = percentageLocation.toInt()

                if (percentageLocationNum in 1..100) {
                    //Insertar datos a la BD
                    val exitoso = locationController.insertLocation(nameLocation, percentageLocationNum)

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
                    Toast.makeText(this, getString(R.string.main_form_location_error_percentage), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show()
            }
        }

        //Botón cancelar
        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }
    }
}