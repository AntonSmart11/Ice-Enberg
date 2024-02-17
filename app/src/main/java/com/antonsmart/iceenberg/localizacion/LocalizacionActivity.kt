package com.antonsmart.iceenberg.localizacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antonsmart.iceenberg.Adapters.LocationAdapter
import com.antonsmart.iceenberg.Controllers.LocationController
import com.antonsmart.iceenberg.Database.DatabaseHelper
import com.antonsmart.iceenberg.Objects.Location
import com.antonsmart.iceenberg.R
import com.antonsmart.iceenberg.databinding.ActivityLocalizacionBinding

class LocalizacionActivity : AppCompatActivity(), LocationAdapter.OnItemClickListener {

    private lateinit var binding: ActivityLocalizacionBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var locationController: LocationController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localizacion)
        binding = ActivityLocalizacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLocalizaciones)

        supportActionBar?.apply {
            title = getString(R.string.main_location_title)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarLocalizaciones.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.addLocalizacion.setOnClickListener {
            val intent = Intent(this, FormLocalizacionActivity::class.java)
            startActivity(intent)
        }

        //conexion a la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        locationController = LocationController(dbHelper)

        //RecyclerView
        val locations = locationController.getLocation()

        val recyclerView: RecyclerView = findViewById(R.id.localizaciones_recyclerView)
        val adapter = LocationAdapter(locations, this)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    override fun onItemClick(location: Location) {
        val intent = Intent(this, FormLocalizacionActivity::class.java)
        intent.putExtra("location", location)
        startActivity(intent)
    }
}