package com.ddm.boogle.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.ddm.boogle.MainActivity
import com.ddm.boogle.databinding.FragmentProfileBinding
import com.ddm.boogle.view.user.login.ResetPassword
import com.google.firebase.auth.FirebaseAuth

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

        // deletar conta
        val deleteAccountButton = binding.buttonDelete
        deleteAccountButton.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            user?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                        requireActivity().finish()
                    }
                }
        }
    }
}