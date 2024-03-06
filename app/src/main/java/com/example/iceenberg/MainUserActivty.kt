package com.example.iceenberg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.iceenberg.Controllers.InstallationController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.admin.mantenimiento.MantenimientoActivity
import com.example.iceenberg.auth.LoginActivity
import com.example.iceenberg.databinding.ActivityMainUserBinding
import com.example.iceenberg.idiomas.IdiomasActivity
import com.example.iceenberg.user.equipos.EquiposActivity
import com.example.iceenberg.user.profile.profileUserActivity
import com.example.iceenberg.user.revisiones.RevisionServiceActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainUserActivty : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
                    val userIntent = Intent(this, profileUserActivity::class.java).apply {
                        //pasar parametros
                        putExtra("email", email)

                    }
                    startActivity(userIntent)
                    true
                }
                R.id.menu_idiomas -> {
                    val intent = Intent(this, IdiomasActivity::class.java)
                    startActivity(intent)
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