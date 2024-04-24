package com.ddm.boogle.view.user.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ddm.boogle.NavBar
import com.ddm.boogle.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val loginButton = binding.button

        loginButton.setOnClickListener {
            val name = binding.nameET.text.toString()
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (name.isNotBlank() && email.isNotBlank() && pass.isNotBlank()) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Criação do usuário bem-sucedida
                            val user = firebaseAuth.currentUser

                            // Verifica se o usuário atual não é nulo e se o nome não está vazio
                            if (user != null && name.isNotBlank()) {
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build()

                                user.updateProfile(profileUpdates)
                                    .addOnCompleteListener { profileUpdateTask ->
                                    }
                            }
                            val intent = Intent(this, NavBar::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Erro ao criar conta: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Campos Em Branco!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
