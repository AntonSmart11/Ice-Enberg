package com.example.iceenberg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.iceenberg.databinding.ActivityMainAdminBinding
import android.view.Gravity
import android.view.MenuItem
import com.example.iceenberg.idiomas.IdiomasActivity
import com.example.iceenberg.admin.instalacion.InstalacionActivity
import com.example.iceenberg.admin.localizacion.LocalizacionActivity
import com.example.iceenberg.admin.mantenimiento.MantenimientoActivity
import com.example.iceenberg.admin.revision.RevisionActivity
import com.google.android.material.navigation.NavigationView

class MainAdminActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar

        toolbar.title = ""

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Manejar clics en elementos del menú
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_idiomas -> {
                    val intent = Intent(this, IdiomasActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.cerrar_sesion -> {
                    // Lógica para la opción 2
                    true
                }
                else -> false
            }
        }

        // Agregar botón de hamburguesa en la barra de acción
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        //Manejar clic en los botones para ir a las páginas correspondientes

        binding.mantenimientoPage.setOnClickListener {
            val intent = Intent(this, MantenimientoActivity::class.java)
            startActivity(intent)
        }
        binding.instalacionPage.setOnClickListener {
            val intent = Intent(this, InstalacionActivity::class.java)
            startActivity(intent)
        }
        binding.revisionPage.setOnClickListener {
            val intent = Intent(this, RevisionActivity::class.java)
            startActivity(intent)
        }
        binding.localizacionPage.setOnClickListener {
            val intent = Intent(this, LocalizacionActivity::class.java)
            startActivity(intent)
        }
    }

    // Manejar clic en el botón de hamburguesa en la barra de acción
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(Gravity.LEFT)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}