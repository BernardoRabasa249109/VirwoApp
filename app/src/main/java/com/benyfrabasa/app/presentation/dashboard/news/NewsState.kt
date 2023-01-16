package com.benyfrabasa.app.presentation.dashboard.news

import com.benyfrabasa.app.domain.model.Article

data class NewsState(
    val showLoader: Boolean = true,
    val showArticles: Boolean = false,
    val articles: List<Article>? = null
)

sealed class NewsEvent {
    data class GetArticles(val topic: String, val date: String, val limit: Int, val language: String) : NewsEvent()
}
