package com.example.iceenberg.idiomas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.iceenberg.MainAdminActivity
import com.example.iceenberg.MyApp
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityIdiomasBinding
import java.util.Locale

class IdiomasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdiomasBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var recreating = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idiomas)
        binding = ActivityIdiomasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarIdiomas)

        supportActionBar?.apply {
            title = getString(R.string.main_language_title)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarIdiomas.setNavigationOnClickListener {
            onBackPressed()
        }

        sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        val myApp = application as MyApp

        val adapter = ArrayAdapter.createFromResource(
            myApp,
            R.array.options_language,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerIdiomas.adapter = adapter

        val seleccionGuardada = sharedPreferences.getInt("seleccionSpinnerIdiomas", 0)

        binding.spinnerIdiomas.setSelection(seleccionGuardada)

        binding.spinnerIdiomas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Acciones a realizar cuando se selecciona una opción

                val sharedPreferencesKey = "seleccionSpinnerIdiomas"
                val previousSelection = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
                    .getInt(sharedPreferencesKey, 0)

                if (position != previousSelection && !recreating) {
                    recreating = true

                    getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
                        .edit()
                        .putInt("seleccionSpinnerIdiomas", position)
                        .apply()

                    var idioma : String = ""

                    if (position == 0) {
                        idioma = "en"
                    } else if (position == 1) {
                        idioma = "es"
                    } else if (position == 2) {
                        idioma = "it"
                    }

                    myApp.cambiarIdioma(idioma)

                    recreating = false

                    Log.d("entro", "si")
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar cuando no se selecciona nada
            }
        }
    }

    private fun cambiarIdioma(idioma: String) {

        val locale = Locale(idioma)

        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)

        recreate()
    }
}