package com.example.iceenberg.user.equipos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityFormEquiposBinding

class FormEquiposActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormEquiposBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_equipos)
        binding = ActivityFormEquiposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFormEquipos)

        binding.toolbarFormEquipos.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}