package com.example.iceenberg.user.equipos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iceenberg.R
import com.example.iceenberg.admin.localizacion.FormLocalizacionActivity
import com.example.iceenberg.databinding.ActivityEquiposBinding
import com.example.iceenberg.databinding.ActivityFormEquiposBinding
import com.example.iceenberg.databinding.ActivityLocalizacionBinding

class EquiposActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEquiposBinding
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

    }
}