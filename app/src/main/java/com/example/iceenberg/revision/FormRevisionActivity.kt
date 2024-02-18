package com.example.iceenberg.revision

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iceenberg.databinding.ActivityFormRevisionBinding

class FormRevisionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormRevisionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRevisionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFormRevisiones)

        supportActionBar?.apply {
            title = "Nueva revisión"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarFormRevisiones.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}