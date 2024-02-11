package com.antonsmart.iceenberg.instalacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.antonsmart.iceenberg.R
import com.antonsmart.iceenberg.databinding.ActivityInstalacionBinding

class InstalacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInstalacionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstalacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarInstalaciones)

        supportActionBar?.apply {
            title = "Instalaciones"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarInstalaciones.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.addInstalacion.setOnClickListener{
            val intent = Intent(this, FormInstalacionActivity::class.java)
            startActivity(intent)
        }


    }
}