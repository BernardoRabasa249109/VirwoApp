package com.benyfrabasa.app.presentation.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.benyfrabasa.app.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding: ActivityHomeBinding

    @Inject
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { state ->
                    renderState(state)
                }
            }
        }

        binding.apply {
            viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = viewPagerAdapter.fragments[position]
            }.attach()
        }

        viewModel.onEvent(HomeEvent.GetUserSession)
    }

    private fun renderState(homeState: HomeState) = with(binding) {
        homeState.apply {
            userNameLabel.text = userName
            emailLabel.text = email
        }
    }
}
