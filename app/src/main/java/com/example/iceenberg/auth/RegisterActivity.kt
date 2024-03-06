package com.example.iceenberg.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.iceenberg.Controllers.InstallationController
import com.example.iceenberg.Controllers.UserController
import com.example.iceenberg.Database.DatabaseHelper
import com.example.iceenberg.MainUserActivty
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var userController: UserController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //conexion a la bd y llamado del controlador
        dbHelper = DatabaseHelper(this)
        userController = UserController(dbHelper)

        setup()
    }

    private fun setup() {
        binding.btnRegister.setOnClickListener {
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {

                        showUserActivity(it.result?.user?.email ?: "")
                        //Insertar datos a la BD
                        userController.insertUser("", "", email, password, "", 0)
                    } else {

                        val errorMessage = it.exception?.message ?: getString(R.string.main_dialog_register_message)
                        if (errorMessage.contains("Password should be at least 6 characters")) {
                            showAlert(getString(R.string.main_dialog_register_message_characters))
                        }
                        else{
                            showAlert(getString(R.string.main_dialog_register_message))
                        }
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showUserActivity(email: String) {
        val userIntent = Intent(this, MainUserActivty::class.java).apply {
            //pasar parametros
            putExtra("email", email)

        }
        startActivity(userIntent)
    }

    private fun showAlert(mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.main_dialog_register_title))
        builder.setMessage(mensaje)
        builder.setPositiveButton(getString(R.string.accept), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}