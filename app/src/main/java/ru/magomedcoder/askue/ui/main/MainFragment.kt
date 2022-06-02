package ru.magomedcoder.askue.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.magomedcoder.askue.databinding.FragmentMainBinding
import ru.magomedcoder.askue.domain.model.ElectronicCounter
import ru.magomedcoder.askue.ui.base.BaseFragment
import ru.magomedcoder.askue.ui.main.adapter.ElectronicCounterAdapter

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel by viewModel<MainViewModel>()

    private lateinit var adapter: ElectronicCounterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getList()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        setupAdapter()
        observeMainDetails()
    }

    private fun setupAdapter() {
        adapter = ElectronicCounterAdapter()
        binding.rvCounterItems.adapter = adapter
    }

    private fun observeMainDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.mainState.collect { state ->
                when (state) {
                    is MainState.Success -> {
                        bindData(item = state.response)
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    is MainState.Failure -> Log.d("DetailError", state.error)
                    is MainState.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                    else -> Unit
                }
            }
        }
    }

    private fun bindData(item: List<ElectronicCounter>) {
        adapter.items = item
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

}