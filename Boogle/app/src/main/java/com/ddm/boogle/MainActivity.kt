package com.ddm.boogle

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ddm.boogle.databinding.ActivityMainBinding
import com.ddm.boogle.view.user.login.LoginActivity
import com.ddm.boogle.view.user.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginButton = binding.loginButton
        val anonymousLoginButton = binding.anonymousLoginButton
        val register = binding.textView

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        anonymousLoginButton.setOnClickListener {
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signInAnonymously()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, NavBarAnonymous::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Erro ao fazer login anonimamente", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

}