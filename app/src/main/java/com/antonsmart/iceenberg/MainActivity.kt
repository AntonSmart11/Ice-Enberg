package com.antonsmart.iceenberg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.antonsmart.iceenberg.instalacion.InstalacionActivity
import com.antonsmart.iceenberg.mantenimiento.MantenimientoActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button = findViewById<Button>(R.id.btnEdit)

        button.setOnClickListener {
            val intent = Intent(this, MantenimientoActivity::class.java)
            startActivity(intent)
        }




    }
}