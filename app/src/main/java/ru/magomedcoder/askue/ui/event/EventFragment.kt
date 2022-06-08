package ru.magomedcoder.askue.ui.event

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
import ru.magomedcoder.askue.databinding.FragmentEventBinding
import ru.magomedcoder.askue.domain.model.ElectronicEvent
import ru.magomedcoder.askue.ui.base.BackPressHandler
import ru.magomedcoder.askue.ui.base.BackPressRegistrar
import ru.magomedcoder.askue.ui.base.BaseFragment
import ru.magomedcoder.askue.ui.event.adapter.EventAdapter

@AndroidEntryPoint
class EventFragment : BaseFragment<FragmentEventBinding>() {

    private val viewModel: EventViewModel by viewModels()
    private lateinit var adapter: EventAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnArchive.setOnClickListener {
            findNavController().navigate(R.id.action_eventFragment_to_archiveFragment)
        }
        binding.btnOut.setOnClickListener {
            findNavController().navigate(R.id.action_eventFragment_to_outFragment)

        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getList()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        setupAdapter()
        observeDetails()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAdapter() {
        adapter = EventAdapter()
        binding.rvItems.adapter = adapter
    }

    private fun observeDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.eventState.collect { state ->
                when (state) {
                    is EventState.Success -> {
                        bindData(item = state.response)
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    is EventState.Failure -> Log.d("DetailError", state.error)
                    is EventState.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                    else -> Unit
                }
            }
        }
    }

    private fun bindData(item: List<ElectronicEvent>) {
        adapter.items = item
    }

    private val backPressHandler = object : BackPressHandler {
        override fun onBackPressed(): Boolean {
            findNavController().navigateUp()
            return false
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? BackPressRegistrar)?.registerHandler(backPressHandler)
    }

    override fun onStop() {
        (activity as? BackPressRegistrar)?.unregisterHandler(backPressHandler)
        super.onStop()
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEventBinding.inflate(inflater, container, false)

}