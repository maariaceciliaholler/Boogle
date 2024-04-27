package com.ddm.boogle.viewmodel.category

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddm.boogle.BookApiService
import com.ddm.boogle.model.api.BookItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RomanceCategoryViewModel : ViewModel() {
    private val _searchResult = MutableLiveData<List<BookItem>>()
    val searchResult: LiveData<List<BookItem>> = _searchResult
    fun searchBooksByRomanceCategory() {
        viewModelScope.launch {
            val books = withContext(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.googleapis.com/books/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(BookApiService::class.java)
                service.searchBooks("categoria:romance")
            }
            _searchResult.postValue(books.items)
            Log.d(TAG, books.toString())
        }
    }
}