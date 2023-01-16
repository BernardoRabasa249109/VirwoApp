package com.benyfrabasa.app.domain.repository

import com.benyfrabasa.app.domain.model.Articles

interface NewsRepository {
    suspend fun getNews(search: String, date: String, pageSize: Int, language: String, apiKey: String): Articles?
}
