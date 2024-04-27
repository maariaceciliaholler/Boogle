package com.ddm.boogle.view.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ddm.boogle.R
import com.ddm.boogle.model.api.BookItem

class CarouselAdapter (private var books: List<BookItem>) :
    RecyclerView.Adapter<CarouselAdapter.BookViewHolder>() {

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_carousel_layout, parent, false
        )
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.itemView.findViewById<TextView>(R.id.titleTextView).text = book.volumeInfo.title
        val coverImageView: ImageView = holder.itemView.findViewById(R.id.coverImageView)
        book.volumeInfo.imageLinks?.thumbnail?.let { thumbnailUrl ->
            Glide.with(holder.itemView.context)
                .load(thumbnailUrl)
                .into(coverImageView)
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }

    fun updateData(newBooks: List<BookItem>) {
        books = newBooks
        notifyDataSetChanged()
    }
}