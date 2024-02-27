package com.example.iceenberg.admin.revision

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.iceenberg.Controllers.RevisionController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.Objects.Revision
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityFormRevisionBinding

class FormRevisionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormRevisionBinding


    private lateinit var dbHelper: DatabaseHelper
    private lateinit var revisionController: RevisionController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormRevisionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarFormRevisiones)


        //Se confirma si llega un dato a la activity
        val intent = intent
        var titleFormRevision = getString(R.string.main_form_revision_title)
        var nameEditText = ""
        var costEditText = ""
        var revisionId = 0

        if (intent != null && intent.hasExtra("revision")) {
            val revision = intent.getSerializableExtra("revision") as? Revision;
            titleFormRevision = getString(R.string.main_form_revision_title_edit)
            revisionId = revision?.id!!
            nameEditText = revision.name
            costEditText = revision.cost.toString()

            binding.buttonsEdit.visibility = View.VISIBLE
        } else {
            binding.buttonsAdd.visibility = View.VISIBLE
        }

        supportActionBar?.apply {
            title = "Nueva revisión"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarFormRevisiones.setNavigationOnClickListener {
            onBackPressed()
        }

        //Botón cancelar
        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }

        //conocexión a la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        revisionController = RevisionController(dbHelper)

        //Declarar las variables a usar
        var nameRevision: String;
        var costRevision: String;
        var costRevisionNum: Double;

        //Setear editText con el texto por defecto
        binding.nombreRevision.setText(nameEditText)
        binding.costoRevision.setText(costEditText.toString())

        //Tomar los datos  del usuario
        binding.btnAdd.setOnClickListener {
            nameRevision = binding.nombreRevision.text.toString()
            costRevision = binding.costoRevision.text.toString()

            if (nameRevision.isNotEmpty() && costRevision.isNotEmpty()) {
                costRevisionNum = costRevision.toDouble()

                //Insertar datos a la BD
                val exitoso = revisionController.insertRevision(nameRevision, costRevisionNum)

                if (exitoso) {
                    Toast.makeText(this, getString(R.string.successInsert), Toast.LENGTH_SHORT)
                        .show()

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

        //Editar datos en la BD
        binding.btnEdit.setOnClickListener {
            nameRevision = binding.nombreRevision.text.toString()
            costRevision = binding.costoRevision.text.toString()

            if (nameRevision.isNotEmpty() && costRevision.isNotEmpty()){
                costRevisionNum = costRevision.toDouble()

                //Insertar datos a la BD
                val exitoso = revisionController.updateRevision(revisionId,nameRevision,costRevisionNum);

                if (exitoso){
                    Toast.makeText(this, getString(R.string.successInsert), Toast.LENGTH_SHORT).show()

                    val handler = Handler()
                    val runnable = Runnable {
                        onBackPressed()
                    }

                    handler.postDelayed(runnable, 150) // 3 segundos de retraso
                } else {
                    Toast.makeText(this, getString(R.string.errorInsert), Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show()
            }
        }

        //Eliminar datos de la BD
        binding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val id = revisionId

            builder.setTitle(getString(R.string.main_dialog_revision_delete_title))
            builder.setMessage(getString(R.string.main_dialog_revision_delete_message))

            builder.setNegativeButton(getString(R.string.cancel)) {dialog, which ->
                dialog.dismiss()
            }

            builder.setPositiveButton(getString(R.string.delete)){dialog, which ->
                val eliminacion = revisionController.deleteRevision(id)

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