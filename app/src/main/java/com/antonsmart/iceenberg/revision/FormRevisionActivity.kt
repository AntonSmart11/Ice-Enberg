package com.antonsmart.iceenberg.revision

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.antonsmart.iceenberg.R
import com.antonsmart.iceenberg.databinding.ActivityFormRevisionBinding
import com.antonsmart.iceenberg.databinding.ActivityRevisionBinding

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