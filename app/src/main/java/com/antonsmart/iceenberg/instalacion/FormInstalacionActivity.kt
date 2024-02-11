package com.antonsmart.iceenberg.instalacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.antonsmart.iceenberg.R
import com.antonsmart.iceenberg.databinding.ActivityFormInstalacionBinding

class FormInstalacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormInstalacionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormInstalacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFormInstalaciones)

        supportActionBar?.apply {
            title = "Nueva Instalación"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarFormInstalaciones.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}