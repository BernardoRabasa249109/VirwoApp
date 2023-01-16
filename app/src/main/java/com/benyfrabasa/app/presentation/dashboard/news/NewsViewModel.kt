package com.benyfrabasa.app.presentation.dashboard.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benyfrabasa.app.domain.useCases.GetNews
import com.benyfrabasa.app.utils.API_KEY_NEWS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNews: GetNews
) : ViewModel() {

    private val _state: MutableStateFlow<NewsState> by lazy { MutableStateFlow(NewsState()) }
    val state: StateFlow<NewsState> by lazy { _state.asStateFlow() }

    fun onEvent(event: NewsEvent) {
        when (event) {
            is NewsEvent.GetArticles -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = getNews.runUseCase(
                        event.topic, event.date, event.limit, event.language, API_KEY_NEWS
                    )
                    result?.let { articles ->
                        _state.emit(
                            NewsState(
                                showLoader = false,
                                showArticles = true,
                                articles = articles.articles
                            )
                        )
                    }
                }
            }
        }
    }
}
