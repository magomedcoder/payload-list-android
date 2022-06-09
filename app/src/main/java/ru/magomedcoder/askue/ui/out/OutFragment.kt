package ru.magomedcoder.askue.ui.out

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.magomedcoder.askue.R
import ru.magomedcoder.askue.databinding.FragmentOutBinding
import ru.magomedcoder.askue.domain.model.ElectronicOut
import ru.magomedcoder.askue.ui.base.BaseFragment
import ru.magomedcoder.askue.ui.out.adapter.OutAdapter

@AndroidEntryPoint
class OutFragment : BaseFragment<FragmentOutBinding>() {

    private val viewModel: OutViewModel by viewModels()
    private lateinit var adapter: OutAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvCenterText.text = getString(R.string.data_wait_please)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getList()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        setupAdapter()
        observeDetails()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAdapter() {
        adapter = OutAdapter()
        binding.rvItems.adapter = adapter
    }

    private fun observeDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.outState.collect { state ->
                when (state) {
                    is OutState.Success -> {
                        bindData(item = state.response)
                        binding.swipeRefreshLayout.isRefreshing = false
                        if (state.response.isEmpty()) {
                            binding.tvCenterText.text = getString(R.string.nothing_found)
                        } else {
                            binding.tvCenterText.visibility = View.GONE
                        }
                    }
                    is OutState.Failure -> Log.d("DetailError", state.error)
                    is OutState.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                    else -> Unit
                }
            }
        }
    }

    private fun bindData(item: List<ElectronicOut>) {
        adapter.items = item
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOutBinding.inflate(inflater, container, false)

}