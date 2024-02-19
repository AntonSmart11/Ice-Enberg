package com.example.iceenberg.mantenimiento

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iceenberg.Adapters.MaintenanceAdapter
import com.example.iceenberg.Controllers.MaintenanceController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Maintenance
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityMantenimientoBinding

class MantenimientoActivity : AppCompatActivity(), MaintenanceAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMantenimientoBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var maintenanceController: MaintenanceController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantenimientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMantenimientos)

        supportActionBar?.apply {
            title = getString(R.string.main_maintenance_title)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarMantenimientos.setNavigationOnClickListener{
            onBackPressed()
        }

        binding.addMantenimiento.setOnClickListener {
            val intent = Intent(this,FormMantenimientoActivity::class.java)
            startActivity(intent)
        }

        //conexión con a la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        maintenanceController = MaintenanceController(dbHelper)

        //ReciclerView
        val maintenances = maintenanceController.getMaintenance()

        val recyclerView: RecyclerView = findViewById(R.id.mantenimientos_recyclerView)
        val adapter = MaintenanceAdapter(maintenances,this)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    override fun onResume() {
        super.onResume()

        // Actualizar la lista de mantenimientos en el RecyclerView
        val maintenances = maintenanceController.getMaintenance()
        (binding.mantenimientosRecyclerView.adapter as? MaintenanceAdapter)?.updateList(maintenances)
    }

    override fun onItemClick(maintenance: Maintenance){
        val intent = Intent(this, FormMantenimientoActivity::class.java)
        intent.putExtra("maintenance",maintenance)
        startActivity(intent)
    }
}