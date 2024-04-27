package com.ddm.boogle.view.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.ddm.boogle.R
import com.ddm.boogle.model.api.VolumeInfo
import com.google.firebase.database.FirebaseDatabase

class BookAdapter(context: Context, books: List<VolumeInfo>) :
    ArrayAdapter<VolumeInfo>(context, 0, books) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val animationView =
            (parent as? ListView)?.rootView?.findViewById<LottieAnimationView>(R.id.animationView)
        if (animationView != null) {
            if (count == 0) {
                animationView.visibility = View.VISIBLE
            } else {
                animationView.visibility = View.GONE
            }
        }

        var listItemView = convertView
        if (listItemView == null) {
            listItemView =
                LayoutInflater.from(context).inflate(R.layout.list_item_book, parent, false)
        }

        val currentBook = getItem(position)

        val textTitle = listItemView?.findViewById<TextView>(R.id.textTitle)
        textTitle?.text = currentBook?.title

        val textAuthors = listItemView?.findViewById<TextView>(R.id.textAuthors)
        textAuthors?.text = currentBook?.authors?.joinToString(", ")

        val imageView = listItemView?.findViewById<ImageView>(R.id.coverImageView)
        currentBook?.imageLinks?.thumbnail?.let { thumbnailUrl ->
            if (imageView != null) {
                Glide.with(context)
                    .load(thumbnailUrl)
                    .into(imageView)
            }
        }

        val removeIcon = listItemView?.findViewById<ImageView>(R.id.removeIcon)
        removeIcon?.setOnClickListener {
            val book = getItem(position)
            if (book != null) {
                val database = FirebaseDatabase.getInstance()
                val favoritesRef = database.getReference("favorite_books")
                favoritesRef.child(book.title.hashCode().toString()).removeValue()
                Toast.makeText(context, "Favorito excluído com sucesso!", Toast.LENGTH_SHORT).show()
            }
        }

        return listItemView!!
    }
}