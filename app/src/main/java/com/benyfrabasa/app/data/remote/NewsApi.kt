package com.benyfrabasa.app.data.remote

import com.benyfrabasa.app.domain.model.Articles
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("q") search: String,
        @Query("from") date: String,
        @Query("pageSize") pageSize: Int,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String,
    ): Articles?
}
