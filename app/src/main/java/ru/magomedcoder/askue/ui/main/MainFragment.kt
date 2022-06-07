package ru.magomedcoder.askue.ui.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.magomedcoder.askue.R
import ru.magomedcoder.askue.databinding.FragmentMainBinding
import ru.magomedcoder.askue.domain.model.ElectronicCounter
import ru.magomedcoder.askue.ui.auth.AuthState
import ru.magomedcoder.askue.ui.auth.AuthViewModel
import ru.magomedcoder.askue.ui.auth.UserState
import ru.magomedcoder.askue.ui.base.BaseFragment
import ru.magomedcoder.askue.ui.main.adapter.ElectronicCounterAdapter
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private val loginViewModel: AuthViewModel by viewModels()

    private lateinit var adapter: ElectronicCounterAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            loginViewModel.authState.collect { state ->
                when (state) {
                    is AuthState.Logged -> {
                        if (!state.logged) {
                            Toast
                                .makeText(
                                    activity,
                                    R.string.you_have_successfully_logged_out,
                                    Toast.LENGTH_SHORT
                                )
                                .show()

                            loginViewModel.getUserInfo()

                            findNavController().navigate(R.id.action_mainFragment_to_authFragment)
                        }
                    }
                    else -> Unit
                }
            }
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getList()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        binding.btnSearch.setOnClickListener {
            showSearchBox()
        }
        binding.btnEvent.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_eventFragment)
        }
        binding.btnAccount.setOnClickListener {
            showAccountBox()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupAdapter()
        }
        observeMainDetails()
        binding.tvFound.visibility = GONE
        binding.tvFound.text = "Найдено: "
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showSearchBox() {
        val messageBoxView = LayoutInflater.from(activity).inflate(R.layout.nav_search_box, null)
        val messageBoxBuilder = AlertDialog.Builder(activity).setView(messageBoxView)
        val messageBoxInstance = messageBoxBuilder.show()
        val etFrom = messageBoxView.findViewById(R.id.etFrom) as TextView
        val etTo = messageBoxView.findViewById(R.id.etTo) as TextView
        val etContractNumber = messageBoxView.findViewById(R.id.etContractNumber) as EditText
        val etSerialNumber = messageBoxView.findViewById(R.id.etSerialNumber) as EditText
        val etLocality = messageBoxView.findViewById(R.id.etLocality) as EditText
        val etStreet = messageBoxView.findViewById(R.id.etStreet) as EditText
        val etNumber = messageBoxView.findViewById(R.id.etNumber) as EditText
        val etApartmentNumber = messageBoxView.findViewById(R.id.etApartmentNumber) as EditText
        val btnReset = messageBoxView.findViewById(R.id.btnReset) as Button
        val btnSearch = messageBoxView.findViewById(R.id.btnSearch) as Button
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
                        "" + year + " - " + (monthOfYear + 1) + " - " + dayOfMonth + " " + hour + ":" + minute
                }, year, month, day)
            }?.show()
        }
        etTo.setOnClickListener {
            activity?.let { itTo ->
                DatePickerDialog(
                    itTo, { _, year, monthOfYear, dayOfMonth ->
                        etTo.text =
                            "" + year + " - " + (monthOfYear + 1) + " - " + dayOfMonth + " " + hour + ":" + minute
                    }, year, month, day
                )
            }?.show()
        }
        btnReset.setOnClickListener {
            etFrom.text = null
            etTo.text = null
            etContractNumber.text = null
            etSerialNumber.text = null
            etLocality.text = null
            etStreet.text = null
            etNumber.text = null
            etApartmentNumber.text = null
            // viewModel.getList()
            binding.tvFound.visibility = GONE
            messageBoxInstance.dismiss()
        }
        btnSearch.setOnClickListener {
            viewModel.getList(
                etFrom.text.toString(),
                etTo.text.toString(),
                etContractNumber.text.toString(),
                etSerialNumber.text.toString(),
                etLocality.text.toString(),
                etStreet.text.toString(),
                etNumber.text.toString(),
                etApartmentNumber.text.toString()
            )
            binding.tvFound.visibility = VISIBLE
            messageBoxInstance.dismiss()
        }
        messageBoxView.setOnClickListener() {
            messageBoxInstance.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showAccountBox() {
        val messageBoxView = LayoutInflater.from(activity).inflate(R.layout.nav_account_box, null)
        val messageBoxBuilder = AlertDialog.Builder(activity).setView(messageBoxView)
        val btnLogout = messageBoxView.findViewById(R.id.btnLogout) as Button
        val messageBoxInstance = messageBoxBuilder.show()
        btnLogout.setOnClickListener {
            loginViewModel.logout()
            messageBoxInstance.dismiss()
        }
        val firstName = messageBoxView.findViewById(R.id.tvFirstName) as TextView
        val lastName = messageBoxView.findViewById(R.id.tvLastName) as TextView
        val organizationName = messageBoxView.findViewById(R.id.tvOrganizationName) as TextView
        val email = messageBoxView.findViewById(R.id.tvEmail) as TextView
        val phoneNumber = messageBoxView.findViewById(R.id.tvPhoneNumber) as TextView
        loginViewModel.getUserInfo()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            loginViewModel.userState.collect { state ->
                when (state) {
                    is UserState.Success -> {
                        btnLogout.text = "@" + state.response.username
                        firstName.text = state.response.firstName
                        lastName.text = state.response.lastName
                        organizationName.text = state.response.organizationName
                        email.text = state.response.email
                        phoneNumber.text = state.response.phoneNumber
                    }
                    else -> Unit
                }
            }
        }
        messageBoxView.setOnClickListener() {
            messageBoxInstance.dismiss()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAdapter() {
        adapter = ElectronicCounterAdapter { item ->
            findNavController()
                .navigate(
                    MainFragmentDirections
                        .actionMainFragmentToDetailElectronicCounterFragment(
                            devEui = item.devEui,
                            serN = item.serN
                        )
                )
        }
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