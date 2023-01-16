package com.benyfrabasa.app.domain.useCases

import com.benyfrabasa.app.domain.model.Articles
import com.benyfrabasa.app.domain.repository.NewsRepository
import javax.inject.Inject

class GetNews @Inject constructor(
    private val repository: NewsRepository
) {
    suspend fun runUseCase(search: String, date: String, pageSize: Int, language: String, apiKey: String): Articles? {
        return repository.getNews(search, date, pageSize, language, apiKey)
    }
}
