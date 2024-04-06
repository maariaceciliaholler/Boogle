package com.ddm.boogle.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Pesquise por um título"
    }
    val text: LiveData<String> = _text

    fun searchBookByTitle(title: String) {
        val apiKey = "AIzaSyBtPCOdgWYKmWEQP4Wh4Z8zyo9yiuM0NGk"
        val query = title.replace(" ", "+")
        val url = URL("https://www.googleapis.com/books/v1/volumes?q=$query&key=$apiKey")
    }
}