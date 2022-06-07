package ru.magomedcoder.askue.ui.archive

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ru.magomedcoder.askue.databinding.FragmentArchiveBinding
import ru.magomedcoder.askue.domain.model.ElectronicArchive
import ru.magomedcoder.askue.ui.archive.adapter.ArchiveAdapter
import ru.magomedcoder.askue.ui.base.BaseFragment

@AndroidEntryPoint
class ArchiveFragment : BaseFragment<FragmentArchiveBinding>() {

    private val viewModel: ArchiveViewModel by viewModels()
    private lateinit var adapter: ArchiveAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getList()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        setupAdapter()
        observeDetails()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAdapter() {
        adapter = ArchiveAdapter()
        binding.rvItems.adapter = adapter
    }

    private fun observeDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.archiveState.collect { state ->
                when (state) {
                    is ArchiveState.Success -> {
                        bindData(item = state.response)
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    is ArchiveState.Failure -> Log.d("DetailError", state.error)
                    is ArchiveState.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                    else -> Unit
                }
            }
        }
    }

    private fun bindData(item: List<ElectronicArchive>) {
        adapter.items = item
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentArchiveBinding.inflate(inflater, container, false)

}