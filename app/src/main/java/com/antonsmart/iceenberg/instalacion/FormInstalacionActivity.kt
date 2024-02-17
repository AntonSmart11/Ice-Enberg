package com.antonsmart.iceenberg.instalacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.antonsmart.iceenberg.Controllers.InstallationController
import com.antonsmart.iceenberg.Database.DatabaseHelper
import com.antonsmart.iceenberg.Objects.Installation
import com.antonsmart.iceenberg.R
import com.antonsmart.iceenberg.databinding.ActivityFormInstalacionBinding

class FormInstalacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormInstalacionBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var installationController: InstallationController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormInstalacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFormInstalaciones)

        //Se confirma si llega un dato a la activity
        val intent = intent
        var titleFormInstallation = getString(R.string.main_form_installation_title)
        var nameEditText = ""
        var constEditText = ""

        if (intent != null && intent.hasExtra("installation")) {
            val installation = intent.getSerializableExtra("installation") as? Installation
            titleFormInstallation = getString(R.string.main_form_installation_title_edit)
            val installationId = installation?.id!!
            nameEditText = installation.name
            constEditText = installation.cost.toString()

            binding.buttonsEdit.visibility = View.VISIBLE
        } else {
           binding.buttonsAdd.visibility = View.VISIBLE
        }

        supportActionBar?.apply {
            title = titleFormInstallation
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarFormInstalaciones.setNavigationOnClickListener {
            onBackPressed()
        }

        //conexion a la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        installationController = InstallationController(dbHelper)

        //Declarar las variables a usar
        var nameInstallation: String
        var costInstallation: String
        var costInstallationNum: Double

        //Setear editText con el texto por defecto
        binding.nombreInstalacion.setText(nameEditText)
        binding.costoInstalacion.setText(constEditText)

        //Tomar los datos del usuario, botón agregar
        binding.btnAdd.setOnClickListener {
            nameInstallation= binding.nombreInstalacion.text.toString()
            costInstallation = binding.costoInstalacion.text.toString()

            if (nameInstallation.isNotEmpty() && costInstallation.isNotEmpty()) {
                costInstallationNum = costInstallation.toDouble()

                //Insertar datos a la BD
                val exitoso = installationController.insertInstallation(nameInstallation, costInstallationNum)

                if (exitoso) {
                    Toast.makeText(this, getString(R.string.successInsert), Toast.LENGTH_SHORT).show()

                    val handler = Handler()
                    val runnable = Runnable {
                        onBackPressed()
                    }

                    handler.postDelayed(runnable, 150)
                } else {
                    Toast.makeText(this, getString(R.string.errorInsert), Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show()
            }
        }

        //Botón cancelar
        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }



    }
}