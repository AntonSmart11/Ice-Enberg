package com.example.iceenberg.user.equipos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iceenberg.Adapters.EquipmentAdapter
import com.example.iceenberg.Controllers.EquipmentController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Equipments
import com.example.iceenberg.R
import com.example.iceenberg.admin.localizacion.FormLocalizacionActivity
import com.example.iceenberg.databinding.ActivityEquiposBinding
import com.example.iceenberg.databinding.ActivityFormEquiposBinding
import com.example.iceenberg.databinding.ActivityLocalizacionBinding

class EquiposActivity : AppCompatActivity(), EquipmentAdapter.OnItemClickListener {

    private lateinit var binding: ActivityEquiposBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var equipmentController: EquipmentController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipos)
        binding = ActivityEquiposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarEquipos)

        supportActionBar?.apply {
            title = getString(R.string.main_user_equipment)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarEquipos.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.addEquipos.setOnClickListener {
            val intent = Intent(this, FormEquiposActivity::class.java)
            startActivity(intent)
        }

        //conexion a la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        equipmentController = EquipmentController(dbHelper)

        //RecyclerView
        val equipments = equipmentController.getEquipment()

        val recyclerView: RecyclerView = findViewById(R.id.equipos_recyclerView)
        val adapter = EquipmentAdapter(equipments, this)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    override fun onResume() {
        super.onResume()

        // Actualizar la lista de equipos en el RecyclcerView
        val equipments = equipmentController.getEquipment()
        (binding.equiposRecyclerView.adapter as? EquipmentAdapter)?.updateList(equipments)
    }

    override fun onItemClick(equipment: Equipments) {
        val intent = Intent(this, FormEquiposActivity::class.java)
        intent.putExtra("equipment", equipment)
        startActivity(intent)
    }

}