package com.antonsmart.iceenberg.localizacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.antonsmart.iceenberg.Controllers.LocationController
import com.antonsmart.iceenberg.Database.DatabaseHelper
import com.antonsmart.iceenberg.R
import com.antonsmart.iceenberg.databinding.ActivityFormLocalizacionBinding

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

        supportActionBar?.apply {
            title = getString(R.string.main_form_location_title)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarFormLocalizaciones.setOnClickListener {
            onBackPressed()
        }


        //conexion a la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        locationController = LocationController(dbHelper)

        //Declarar las variables a usar
        var nameLocation: String;
        var percentageLocation: String
        var percentageLocationNum: Int;

        //Tomar los datos del usuario
        binding.btnEdit.setOnClickListener {
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
    }
}