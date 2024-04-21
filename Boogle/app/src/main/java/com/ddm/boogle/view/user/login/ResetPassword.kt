package com.ddm.boogle.view.user.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ddm.boogle.databinding.ActivityResetPasswordBinding
import com.ddm.boogle.model.notification.FirebaseMessagingAdapter
import com.google.firebase.auth.FirebaseAuth

class ResetPassword : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var firebaseMessagingAdapter: FirebaseMessagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        firebaseMessagingAdapter = FirebaseMessagingAdapter(this)

        val resetButton = binding.button

        resetButton.setOnClickListener {
            val email = binding.emailEt.text.toString()

            if (email.isNotBlank()) {
                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Um e-mail de redefinição de senha foi enviado para $email",
                                Toast.LENGTH_SHORT
                            ).show()
                            firebaseMessagingAdapter.showNotification("Redefinição de senha", "Um e-mail de redefinição de senha foi enviado para $email")
                        } else {
                            Toast.makeText(
                                this,
                                "Falha ao enviar e-mail de redefinição de senha. Por favor, verifique o e-mail inserido.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor, insira seu e-mail.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
