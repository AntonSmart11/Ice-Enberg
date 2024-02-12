package com.antonsmart.iceenberg.mantenimiento

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.antonsmart.iceenberg.R
import com.antonsmart.iceenberg.databinding.ActivityMantenimientoBinding

class MantenimientoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMantenimientoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantenimientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMantenimientos)

        supportActionBar?.apply {
            title = "Mantenimientos"
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
    }
}