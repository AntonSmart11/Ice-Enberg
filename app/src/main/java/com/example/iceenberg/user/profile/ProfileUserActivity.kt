package com.example.iceenberg.user.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.iceenberg.Controllers.UserController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.R
import com.example.iceenberg.auth.LoginActivity
import com.example.iceenberg.databinding.ActivityProfileUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileUserBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var userController: UserController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        binding = ActivityProfileUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarProfileUser)

        supportActionBar?.apply {
            title = getString(R.string.profile)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbarProfileUser.setNavigationOnClickListener {
            onBackPressed()
        }

        //recibiendo parametros
        val bundle = intent.extras
        val email = bundle?.getString("email")

        dbHelper = DatabaseHelper(this)
        userController = UserController(dbHelper)

        val user = dbHelper.getUserByEmail(email ?: "")

        val userId = user?.id

        binding.nombreUsuario.setText(user?.name)
        binding.apellidoUsuario.setText(user?.lastName)
        binding.emailUsuario.setText(user?.email)
        binding.passwordUsuario.setText(user?.password)
        binding.telefonoUsuario.setText(user?.phone)

        //Declarar las variables a usar
        var nameUser: String
        var lastNameUser: String
        var emailUser: String
        var passwordUser: String
        var phoneUser: String

        val currentUser = FirebaseAuth.getInstance().currentUser

        binding.btnSave.setOnClickListener {
            nameUser = binding.nombreUsuario.text.toString()
            lastNameUser = binding.apellidoUsuario.text.toString()
            emailUser = binding.emailUsuario.text.toString()
            passwordUser = binding.passwordUsuario.text.toString()
            phoneUser = binding.telefonoUsuario.text.toString()

            if (emailUser.isNotEmpty() && passwordUser.isNotEmpty()) {
                currentUser?.updatePassword(passwordUser)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        //actualizar demas campos en db local
                        userController.updateUser(userId ?: 0, nameUser, lastNameUser, emailUser, passwordUser, phoneUser)
                        Toast.makeText(this, getString(R.string.main_toast_profile_update), Toast.LENGTH_SHORT).show()
                        val handler = Handler()
                        val runnable = Runnable {
                            onBackPressed()
                        }
                        handler.postDelayed(runnable, 150) // 3 segundos de retraso
                    } else {
                        showAlert(getString(R.string.main_dialog_user_update_message))
                        //val errorMessage = it.exception?.message ?: "Error desconocido "
                        //Log.e("RegisterActivity", "Error usuario: $errorMessage")
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnDelete.setOnClickListener {
            deleteDialog(userId ?: 0, currentUser)
        }
    }

    private fun showAlert(mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.main_dialog_user_update_title))
        builder.setMessage(mensaje)
        builder.setPositiveButton(R.string.accept, null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun deleteDialog(idUser: Int, currentUser: FirebaseUser?) {
        val builder = android.app.AlertDialog.Builder(this)
        val id = idUser

        builder.setTitle(getString(R.string.main_dialog_user_delete_title))
        builder.setMessage(getString(R.string.main_dialog_user_delete_message))

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
            dialog.dismiss()
        }

        builder.setPositiveButton(getString(R.string.delete)) { dialog, which ->
            currentUser?.delete()?.addOnCompleteListener{
                if (it.isSuccessful) {
                    userController.deleteUser(id)
                    Toast.makeText(this, getString(R.string.successDelete), Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }

        // Crear y mostrar el diálogo
        val dialog = builder.create()
        dialog.show()
    }

}