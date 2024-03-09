package com.example.iceenberg.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.iceenberg.MainAdminActivity
import com.example.iceenberg.MainUserActivty
import com.example.iceenberg.R
import com.example.iceenberg.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        setup()
    }

    private fun setup() {
        binding.btnEnter.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        var email = currentUser?.email
                        if (email == "admin@admin.com") {
                            showAdminActivity(it.result?.user?.email ?: "")
                        } else {
                            showUserActivity(it.result?.user?.email ?: "")
                        }
                    } else {
                        showAlert(getString(R.string.main_dialog_login_message))
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAdminActivity(email: String) {
        val userIntent = Intent(this, MainAdminActivity::class.java).apply {
            //pasar parametros
            putExtra("email", email)
        }
        startActivity(userIntent)
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
        builder.setTitle(getString(R.string.main_dialog_login_title))
        builder.setMessage(mensaje)
        builder.setPositiveButton(getString(R.string.accept), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}