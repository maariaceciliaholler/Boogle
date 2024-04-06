package com.ddm.boogle

import com.ddm.boogle.model.api.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiService {
    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): BookResponse
}