package com.example.iceenberg.user.Servicios

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iceenberg.Adapters.ServiceAdapter
import com.example.iceenberg.Controllers.ServiceController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Global
import com.example.iceenberg.Objects.Service
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var serviceController: ServiceController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarServicios)

        binding.toolbarServicios.setNavigationOnClickListener {
            onBackPressed()
        }

        supportActionBar?.apply {
            title = getString(R.string.main_service_title)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        //conexión con l bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        serviceController = ServiceController(dbHelper)

        //obtener el usuario loggueado
        val usuario = Global.usuarioCorreo

        //RecyclerView
        val services = serviceController.getService(usuario)

        val recyclerView: RecyclerView = findViewById(R.id.servicios_recyclerView)
        val adapter = ServiceAdapter(services,this)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

    }

    override fun onResume() {
        super.onResume()

        //Actualizar la lista de servicios en el RecyclerView
        val services = serviceController.getService(Global.usuarioCorreo)
        (binding.serviciosRecyclerView.adapter as? ServiceAdapter)?.updateList(services)
    }

    fun onItemClick(service: Service){
        val intent = Intent(this,FormServiceActivity::class.java)
        intent.putExtra("service",service)
        startActivity(intent)
    }


}