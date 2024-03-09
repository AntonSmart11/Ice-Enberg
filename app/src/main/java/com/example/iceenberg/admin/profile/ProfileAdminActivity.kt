package com.example.iceenberg.admin.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityProfileAdminBinding

class ProfileAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        binding = ActivityProfileAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarProfileAdmin)

        supportActionBar?.apply {
            title = getString(R.string.profile)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarProfileAdmin.setNavigationOnClickListener {
            onBackPressed()
        }

        //recibiendo parametros
        val bundle = intent.extras
        val email = bundle?.getString("email")

        binding.emailAdmin.text = email
    }
}