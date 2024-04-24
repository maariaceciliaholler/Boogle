package com.ddm.boogle.view.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ddm.boogle.R
import com.ddm.boogle.databinding.FragmentHomeBinding
import com.ddm.boogle.model.api.BookItem
import com.ddm.boogle.viewmodel.home.HomeViewModel
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
        } else {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val userId = currentUser?.uid

        val searchButton: MaterialButton = binding.searchButton
        searchButton.setOnClickListener {
            val title = binding.titleInput.text.toString()
            homeViewModel.searchBookByTitle(title)
        }

        homeViewModel.searchResult.observe(viewLifecycleOwner, Observer { result ->
            binding.resultLayout.removeAllViews()

                result.forEach { bookItem ->
                    val bookView = inflater.inflate(R.layout.book_item_layout, null)
                    val titleTextView: TextView = bookView.findViewById(R.id.titleTextView)
                    titleTextView.text = bookItem.volumeInfo.title
                    titleTextView.setOnClickListener {
                        showBookDescriptionPopup(bookItem, userId.toString())
                    }
                    binding.resultLayout.addView(bookView)
                }
        })

        askNotificationPermission()

        return root
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showBookDescriptionPopup(bookItem: BookItem, userId: String) {
        val inflater = requireContext().getSystemService(LayoutInflater::class.java)
        val popupView = inflater.inflate(R.layout.book_description_popup, null)

        val popupTitleTextView: TextView = popupView.findViewById(R.id.popupTitleTextView)
        val popupDescriptionTextView: TextView = popupView.findViewById(R.id.popupDescriptionTextView)
        val closeButton: Button = popupView.findViewById(R.id.closeButton)
        val favoriteButton: Button = popupView.findViewById(R.id.favoriteButton)

        popupTitleTextView.text = bookItem.volumeInfo.title
        popupDescriptionTextView.text = bookItem.volumeInfo.description

        val scrollView = ScrollView(requireContext())
        scrollView.addView(popupView)

        val coverImageView: ImageView = popupView.findViewById(R.id.coverImageView)

        bookItem.volumeInfo.imageLinks?.thumbnail?.let { thumbnailUrl ->
            Glide.with(this)
                .load(thumbnailUrl)
                .into(coverImageView)
        }

        val popupWindow = PopupWindow(scrollView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindow.showAtLocation(binding.root, Gravity.CENTER, 0, 0)

        closeButton.setOnClickListener {
            popupWindow.dismiss()
        }

        favoriteButton.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("favorite_books")
            val userInfo = mapOf("uid" to userId)

            val key = myRef.push().key
            key?.let { key ->
                val dataToSave = mapOf(
                    "bookInfo" to bookItem,
                    "userInfo" to userInfo
                )

                myRef.child(key).setValue(dataToSave)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Livro adicionado aos favoritos!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(requireContext(), "Erro ao adicionar o livro aos favoritos! : ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            popupWindow.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}