package com.benyfrabasa.app.data.repository

import com.benyfrabasa.app.data.remote.NewsService
import com.benyfrabasa.app.domain.model.Articles
import com.benyfrabasa.app.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService
): NewsRepository {

    override suspend fun getNews(search: String, date: String, pageSize: Int, language: String, apiKey: String): Articles? {
        return newsService.getNews(search, date, pageSize, language, apiKey)
    }
}
