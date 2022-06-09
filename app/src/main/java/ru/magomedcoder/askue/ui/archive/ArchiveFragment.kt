package ru.magomedcoder.askue.ui.archive

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.magomedcoder.askue.R
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
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvCenterText.text = getString(R.string.search_parameters_specified)
        var swipeRefreshLayout = false
        setupAdapter()
        observeDetails()
        binding.btnNavSearch.setOnClickListener {
            swipeRefreshLayout = showSearchBox()
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (swipeRefreshLayout) {
                // viewModel.getList()
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showSearchBox(): Boolean {
        val messageBoxView =
            LayoutInflater.from(activity).inflate(R.layout.nav_archive_search_box, null)
        val messageBoxBuilder = AlertDialog.Builder(activity).setView(messageBoxView)
        val messageBoxInstance = messageBoxBuilder.show()
        val etFrom = messageBoxView.findViewById(R.id.etFrom) as TextView
        val etTo = messageBoxView.findViewById(R.id.etTo) as TextView
        val cbRed = messageBoxView.findViewById(R.id.cbRed) as CheckBox
        val cbOrange = messageBoxView.findViewById(R.id.cbOrange) as CheckBox
        val cbYellow = messageBoxView.findViewById(R.id.cbYellow) as CheckBox
        val btnReset = messageBoxView.findViewById(R.id.btnReset) as Button
        val btnSearch = messageBoxView.findViewById(R.id.btnSearch) as Button
        var swipeRefreshLayout = false
        btnReset.isEnabled = false
        btnSearch.isEnabled = false
        cbRed.setOnClickListener {
            if (cbRed.isChecked()) {
                swipeRefreshLayout = true
                btnReset.isEnabled = true
                btnSearch.isEnabled = true
                cbOrange.isEnabled = false
                cbYellow.isEnabled = false
            } else {
                btnReset.isEnabled = false
                btnSearch.isEnabled = false
                cbOrange.isEnabled = true
                cbYellow.isEnabled = true
            }
        }
        cbOrange.setOnClickListener {
            if (cbOrange.isChecked()) {
                swipeRefreshLayout = true
                btnReset.isEnabled = true
                btnSearch.isEnabled = true
                cbRed.isEnabled = false
                cbYellow.isEnabled = false
            } else {
                btnReset.isEnabled = false
                btnSearch.isEnabled = false
                cbRed.isEnabled = true
                cbYellow.isEnabled = true
            }
        }
        cbYellow.setOnClickListener {
            if (cbYellow.isChecked()) {
                swipeRefreshLayout = true
                btnReset.isEnabled = true
                btnSearch.isEnabled = true
                cbRed.isEnabled = false
                cbOrange.isEnabled = false
            } else {
                btnReset.isEnabled = false
                btnSearch.isEnabled = false
                cbRed.isEnabled = true
                cbOrange.isEnabled = true
            }
        }
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        etFrom.setOnClickListener {
            activity?.let { itFrom ->
                DatePickerDialog(itFrom, { _, year, monthOfYear, dayOfMonth ->
                    etFrom.text =
                        "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + hour + ":" + minute
                }, year, month, day)
            }?.show()
        }
        etTo.setOnClickListener {
            activity?.let { itTo ->
                DatePickerDialog(
                    itTo, { _, year, monthOfYear, dayOfMonth ->
                        etTo.text =
                            "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + hour + ":" + minute
                    }, year, month, day
                )
            }?.show()
        }
        btnReset.setOnClickListener {
            etFrom.text = null
            etTo.text = null
        }
        btnSearch.setOnClickListener {
            binding.tvCenterText.text = getString(R.string.data_wait_please)
            viewModel.getList(etFrom.text.toString(), etTo.text.toString())
            messageBoxInstance.dismiss()
        }
        messageBoxView.setOnClickListener() {
            messageBoxInstance.dismiss()
        }
        return swipeRefreshLayout
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
                        if (state.response.isEmpty()) {
                            binding.tvCenterText.text = getString(R.string.nothing_found)
                        } else {
                            binding.tvCenterText.visibility = View.GONE
                        }
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