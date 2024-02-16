package com.antonsmart.iceenberg.revision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.antonsmart.iceenberg.R
import com.antonsmart.iceenberg.databinding.ActivityRevisionBinding

class RevisionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRevisionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRevisionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarRevisiones)

        supportActionBar?.apply {
            title = "revisiones"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarRevisiones.setNavigationOnClickListener{
            onBackPressed()
        }

        binding.addRevision.setOnClickListener {
            val intent = Intent(this,FormRevisionActivity::class.java)
            startActivity(intent)
        }
    }

}