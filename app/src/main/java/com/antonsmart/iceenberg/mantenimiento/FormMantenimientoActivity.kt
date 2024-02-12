package com.antonsmart.iceenberg.mantenimiento

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.antonsmart.iceenberg.R
import com.antonsmart.iceenberg.databinding.ActivityFormMantenimientoBinding

class FormMantenimientoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormMantenimientoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormMantenimientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFormMantenimientos)

        supportActionBar?.apply {
            title = "Nuevo mantenimiento"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarFormMantenimientos.setNavigationOnClickListener{
            onBackPressed()
        }
    }
}