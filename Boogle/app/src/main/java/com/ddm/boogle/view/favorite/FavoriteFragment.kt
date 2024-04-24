package com.ddm.boogle.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ddm.boogle.databinding.FragmentFavoriteBinding
import com.ddm.boogle.model.api.ImageLink
import com.ddm.boogle.model.api.VolumeInfo
import com.ddm.boogle.viewmodel.favorite.FavoriteViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var favoriteBooksListener: ValueEventListener
    private lateinit var listView: ListView
    private lateinit var bookAdapter: BookAdapter
    private val volumeInfoList = mutableListOf<VolumeInfo>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firebaseAuth = FirebaseAuth.getInstance()
        listView = binding.listView
        bookAdapter = BookAdapter(requireContext(), volumeInfoList)
        listView.adapter = bookAdapter

        val currentUser = firebaseAuth.currentUser
        val userId = currentUser?.uid.toString()

        if (userId.isNotEmpty()) {
            databaseReference = FirebaseDatabase.getInstance().getReference("favorite_books")
            favoriteBooksListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    volumeInfoList.clear()
                    for (data in snapshot.children) {
                        val bookInfo = data.child("bookInfo").getValue(object : GenericTypeIndicator<Map<String, Any>>() {})
                        val userInfo = data.child("userInfo").getValue(object : GenericTypeIndicator<Map<String, Any>>() {})
                        val uid = userInfo?.get("uid")

                        if(userId == uid.toString()){
                            val volumeInfoMap = bookInfo?.get("volumeInfo") as? Map<String, Any>
                            if (volumeInfoMap != null) {
                                val authorsList = volumeInfoMap["authors"] as? List<String> ?: emptyList()
                                val imageLinksMap = volumeInfoMap["imageLinks"] as? Map<String, String>
                                val imageLinks = if (imageLinksMap != null) {
                                    ImageLink(
                                        smallThumbnail = imageLinksMap["smallThumbnail"] ?: "",
                                        thumbnail = imageLinksMap["thumbnail"] ?: ""
                                    )
                                } else {
                                    null
                                }

                                val volumeInfo = VolumeInfo(
                                    title = volumeInfoMap["title"] as? String ?: "",
                                    authors = authorsList,
                                    description = volumeInfoMap["description"] as? String,
                                    publishedDate = volumeInfoMap["publishedDate"] as? String,
                                    infoLink = volumeInfoMap["infoLink"] as? String ?: "",
                                    imageLinks = imageLinks
                                )
                                volumeInfoList.add(volumeInfo)
                            }
                        }
                    }
                    bookAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }
            databaseReference.addValueEventListener(favoriteBooksListener)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}