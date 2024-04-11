package com.ddm.boogle.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ddm.boogle.R
import com.ddm.boogle.databinding.FragmentHomeBinding
import com.ddm.boogle.viewmodel.home.HomeViewModel
import com.google.android.material.button.MaterialButton

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
                    binding.resultLayout.addView(bookView)
                }
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}