package com.ddm.boogle.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.ddm.boogle.MainActivity
import com.ddm.boogle.databinding.FragmentProfileBinding
import com.ddm.boogle.view.user.login.ResetPassword
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //pegar e-mail e nome atuais
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val name = currentUser.displayName
            val email = currentUser.email

            binding.nameET.setText(name)
            binding.emailEt.setText(email)
        }

        //alterar nome e senhas atuais
        val saveButton = binding.buttonSave
        saveButton.setOnClickListener {
            val newName = binding.nameET.text.toString()
            val newEmail = binding.emailEt.text.toString()

            currentUser?.let { user ->
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build()

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Perfil atualizado com sucesso", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Falha ao atualizar o perfil", Toast.LENGTH_SHORT).show()
                        }
                    }

                val credential = EmailAuthProvider.getCredential(user.email!!, "senha_atual_do_usuario")
                user.reauthenticate(credential)
                    .addOnCompleteListener { reauthTask ->
                        if (reauthTask.isSuccessful) {
                            user.updateEmail(newEmail)
                                .addOnCompleteListener { emailTask ->
                                    if (emailTask.isSuccessful) {
                                        Toast.makeText(requireContext(), "Email atualizado com sucesso", Toast.LENGTH_SHORT).show()
                                    } else {
                                        val exception = emailTask.exception
                                        if (exception is FirebaseAuthException) {
                                            when (exception.errorCode) {
                                                "OPERATION_NOT_ALLOWED" -> {
                                                    Toast.makeText(requireContext(), "Operação não permitida", Toast.LENGTH_SHORT).show()
                                                }
                                                else -> {
                                                    Toast.makeText(requireContext(), "Falha ao atualizar o email", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } else {
                                            Toast.makeText(requireContext(), "Falha ao atualizar o email", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                        } else {
                            Toast.makeText(requireContext(), "Falha na reautenticação", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        // fazer logoff
        val logoffButton: AppCompatButton = binding.buttonLogoff
        logoffButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }

        //resetar senha
        val resetPassword = binding.buttonPassword
        resetPassword.setOnClickListener {
            val intent = Intent(requireContext(), ResetPassword::class.java)
            startActivity(intent)
        }
    }
}