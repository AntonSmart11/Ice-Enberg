package com.example.iceenberg

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.auth.LoginActivity
import com.example.iceenberg.databinding.ActivityMainUserBinding
import com.example.iceenberg.user.Instalaciones.InstallationServiceActivity
import com.example.iceenberg.user.Servicios.ServiceActivity
import com.example.iceenberg.user.equipos.EquiposActivity
import com.example.iceenberg.user.mantenimientos.MaintenanceServiceActivity
import com.example.iceenberg.user.profile.ProfileUserActivity
import com.example.iceenberg.user.revisiones.RevisionServiceActivity
import com.google.firebase.auth.FirebaseAuth

class MainUserActivty : AppCompatActivity() {

    private lateinit var binding: ActivityMainUserBinding
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user)
        binding = ActivityMainUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar

        toolbar.title = ""

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)

        //recibiendo parametros
        val bundle = intent.extras
        val email = bundle?.getString("email")

        dbHelper = DatabaseHelper(this)

        val user = dbHelper.getUserByEmail(email ?: "")

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Manejar clics en elementos del menú
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_perfil -> {
                    val userIntent = Intent(this, ProfileUserActivity::class.java).apply {
                        //pasar parametros
                        putExtra("email", email)

                    }
                    startActivity(userIntent)
                    true
                }
                R.id.cerrar_sesion -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

        // Agregar botón de hamburguesa en la barra de acción
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        //Manejar clic en los botones para ir a las páginas correspondientes

        binding.equiposPage.setOnClickListener {
            val intent = Intent(this, EquiposActivity::class.java)
            startActivity(intent)
        }

        binding.revisionPage.setOnClickListener {
            val intent = Intent(this, RevisionServiceActivity::class.java)
            startActivity(intent)
        }

        binding.instalacionPage.setOnClickListener {
            val intent = Intent(this,InstallationServiceActivity::class.java)
            startActivity(intent)
        }

        binding.mantenimientoPage.setOnClickListener {
            val intent = Intent(this, MaintenanceServiceActivity::class.java)
            startActivity(intent)
        }

        binding.servicesPage.setOnClickListener {
            val intent = Intent(this,ServiceActivity::class.java)
            startActivity(intent)
        }
    }
}