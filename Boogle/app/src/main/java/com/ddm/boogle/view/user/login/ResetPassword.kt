package com.ddm.boogle.view.user.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ddm.boogle.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage

class ResetPassword : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

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

                            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val token = task.result
                                    val message = RemoteMessage.Builder("658480956706@fcm.googleapis.com")
                                        .setMessageId(Integer.toString(0))
                                        .addData("title", "Redefinição de senha")
                                        .addData("message", "Um e-mail de redefinição de senha foi enviado para $email")
                                        .addData("email", email)
                                        .build()

                                    FirebaseMessaging.getInstance().send(message)
                                } else {
                                    // Handle error
                                }
                            }
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