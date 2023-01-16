package com.benyfrabasa.app.data.remote

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsService @Inject constructor(
    private val retrofit: Retrofit
) : NewsApi {
    private val newsApi by lazy { retrofit.create(NewsApi::class.java) }

    override suspend fun getNews(search: String, date: String, pageSize: Int, language: String, apiKey: String) =
        newsApi.getNews(search, date, pageSize, language, apiKey)
}
