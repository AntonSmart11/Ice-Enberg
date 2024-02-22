package com.example.iceenberg.instalacion

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.example.iceenberg.Controllers.InstallationController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Installation
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityFormInstalacionBinding

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
        var installationId = 0

        if (intent != null && intent.hasExtra("installation")) {
            val installation = intent.getSerializableExtra("installation") as? Installation
            titleFormInstallation = getString(R.string.main_form_installation_title_edit)
            installationId = installation?.id!!
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

        //eliminar
        binding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val id = installationId

            builder.setTitle(getString(R.string.main_dialog_delete_title))
            builder.setMessage(getString(R.string.main_dialog_delete_message))

            builder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }

            builder.setPositiveButton(getString(R.string.delete)) { dialog, which ->
                val eliminacion = installationController.deleteInstallation(id)
                if(eliminacion) {
                    dialog.dismiss()
                    Toast.makeText(this, getString(R.string.successDelete), Toast.LENGTH_SHORT).show()
                    onBackPressed()
                } else {
                    dialog.dismiss()
                    Toast.makeText(this, getString(R.string.errorDelete), Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }

            // Crear y mostrar el diálogo
            val dialog = builder.create()
            dialog.show()
        }

    }
}