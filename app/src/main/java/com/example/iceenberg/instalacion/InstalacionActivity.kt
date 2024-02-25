package com.example.iceenberg.instalacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iceenberg.Adapters.InstallationAdapter
import com.example.iceenberg.Adapters.LocationAdapter
import com.example.iceenberg.Controllers.InstallationController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Installation
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityInstalacionBinding

class InstalacionActivity : AppCompatActivity(), InstallationAdapter.OnItemClickListener {

    private lateinit var binding: ActivityInstalacionBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var installationController: InstallationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstalacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarInstalaciones)

        supportActionBar?.apply {
            title = getString(R.string.main_installation_title)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarInstalaciones.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.addInstalacion.setOnClickListener {
            val intent = Intent(this, FormInstalacionActivity::class.java)
            startActivity(intent)
        }

        //conexion a la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        installationController = InstallationController(dbHelper)

        //RecyclerView
        val installations = installationController.getInstallation()

        val recyclerView: RecyclerView = findViewById(R.id.instalaciones_recyclerView)
        val adapter = InstallationAdapter(installations, this)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    override fun onResume() {
        super.onResume()

        // Actualizar la lista de instalaciones en el RecyclerView
        val installations = installationController.getInstallation()
        (binding.instalacionesRecyclerView.adapter as? InstallationAdapter)?.updateList(installations)
    }

    override fun onItemClick(installation: Installation) {
        val intent = Intent(this, FormInstalacionActivity::class.java)
        intent.putExtra("installation", installation)
        startActivity(intent)
    }

}