package com.ddm.boogle.viewmodel.home

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

class HomeViewModel : ViewModel() {

    private val _searchResult = MutableLiveData<List<BookItem>>()
    val searchResult: LiveData<List<BookItem>> = _searchResult

    fun searchBookByTitle(title: String) {
        viewModelScope.launch {
            val books = withContext(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.googleapis.com/books/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(BookApiService::class.java)
                service.searchBooks(title)
            }
            _searchResult.postValue(books.items)
        }
    }

}