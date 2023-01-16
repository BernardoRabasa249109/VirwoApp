package com.benyfrabasa.app.presentation.dashboard.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.benyfrabasa.app.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private val viewModel by viewModels<NewsViewModel>()
    private var binding: FragmentNewsBinding? = null

    @Inject
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    renderState(state)
                }
            }
        }

        setAdapter()

        viewModel.onEvent(
            NewsEvent.GetArticles(
                topic = "chatGPT",
                date = SimpleDateFormat("yyyy-MM-dd").format(Date()),
                limit = 20,
                language = "en"
            )
        )
    }

    private fun setAdapter() {
        binding?.apply {
            newsRecycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = newsAdapter
            }
        }
    }

    private fun renderState(newsState: NewsState) {
        binding?.apply {
            newsState.apply {
                progressBar.isVisible = showLoader
                newsRecycler.isVisible = showArticles
                articles?.let { newsAdapter.setData(it) }
            }
        }
    }
}
