package com.ddm.boogle.view.home

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ddm.boogle.R
import com.ddm.boogle.databinding.FragmentHomeBinding
import com.ddm.boogle.model.api.BookItem
import com.ddm.boogle.viewmodel.home.HomeViewModel
import com.google.android.material.button.MaterialButton

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val searchButton: MaterialButton = binding.searchButton
        searchButton.setOnClickListener {
            val title = binding.titleInput.text.toString()
            homeViewModel.searchBookByTitle(title)
        }

        homeViewModel.searchResult.observe(viewLifecycleOwner, Observer { result ->
            binding.resultLayout.removeAllViews()

            if (result.isNullOrEmpty()) {
                binding.animationView.visibility = View.VISIBLE
            } else {
                binding.animationView.visibility = View.GONE
                result.forEach { bookItem ->
                    val bookView = inflater.inflate(R.layout.book_item_layout, null)
                    val titleTextView: TextView = bookView.findViewById(R.id.titleTextView)
                    titleTextView.text = bookItem.volumeInfo.title
                    titleTextView.setOnClickListener {
                        showBookDescriptionPopup(bookItem)
                    }
                    binding.resultLayout.addView(bookView)
                }
            }
        })

        return root
    }

    private fun showBookDescriptionPopup(bookItem: BookItem) {
        val inflater = requireContext().getSystemService(LayoutInflater::class.java)
        val popupView = inflater.inflate(R.layout.book_description_popup, null)

        val popupTitleTextView: TextView = popupView.findViewById(R.id.popupTitleTextView)
        val popupDescriptionTextView: TextView = popupView.findViewById(R.id.popupDescriptionTextView)
        val closeButton: Button = popupView.findViewById(R.id.closeButton)

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}