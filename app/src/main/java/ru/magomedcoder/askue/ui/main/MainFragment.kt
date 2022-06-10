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
import androidx.constraintlayout.widget.ConstraintLayout
import ru.magomedcoder.askue.ui.base.BackPressHandler
import ru.magomedcoder.askue.ui.base.BackPressRegistrar
import ru.magomedcoder.askue.utils.convertFormatDateFromIso
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private val loginViewModel: AuthViewModel by viewModels()

    private lateinit var adapter: ElectronicCounterAdapter

    var backPressedTime: Long = 0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getList() // TODO
        viewModel.getCounter()
        binding.tvCenterText.text = getString(R.string.data_wait_please)
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
            viewModel.getCounter()
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
        observeDetails()
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
            showDetailElectronicCounterBox(item)
        }
        binding.rvCounterItems.adapter = adapter
    }

    private fun observeDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.mainState.collect { state ->
                when (state) {
                    is MainState.Success -> {
                        bindData(item = state.response)
                        binding.swipeRefreshLayout.isRefreshing = false
                        if (state.response.isEmpty()) {
                            binding.tvCenterText.text = getString(R.string.nothing_found)
                        } else {
                            binding.tvCenterText.visibility = View.GONE
                        }
                    }
                    is MainState.Failure -> Log.d("DetailError", state.error)
                    is MainState.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                    else -> Unit
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.mainState.collect { state ->
                when (state) {
                    is MainState.Counter -> {
                        binding.apply {
                            tvRed.text = state.event.redLevel
                            tvOrange.text = state.event.orangeLevel
                            tvYellow.text = state.event.yellowLevel
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun bindData(item: List<ElectronicCounter>) {
        adapter.items = item
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun showDetailElectronicCounterBox(item: ElectronicCounter) {
        val messageBoxView =
            LayoutInflater.from(activity).inflate(R.layout.detail_electronic_counter_box, null)
        val messageBoxBuilder = AlertDialog.Builder(activity).setView(messageBoxView)
        val messageBoxInstance = messageBoxBuilder.show()
        val personalAccountBox = messageBoxView.findViewById(R.id.tvPersonalAccountBox) as TextView
        val addressBox = messageBoxView.findViewById(R.id.tvAddressBox) as TextView
        val counterModelBox = messageBoxView.findViewById(R.id.tvCounterModelBox) as TextView
        val serNBox = messageBoxView.findViewById(R.id.tvserNBox) as TextView
        val lastSeenAtBox = messageBoxView.findViewById(R.id.tvLastSeenAtBox) as TextView
        val tvReadingTime = messageBoxView.findViewById(R.id.tvReadingTime) as TextView
        val beginningPeriod = messageBoxView.findViewById(R.id.tvBeginningPeriod) as TextView
        val endPeriod = messageBoxView.findViewById(R.id.tvEndPeriod) as TextView
        val forPeriod = messageBoxView.findViewById(R.id.tvForPeriod) as TextView
        val btnStatusCheck = messageBoxView.findViewById(R.id.btnStatusCheck) as TextView
        val address = item.placeAddress
        personalAccountBox.text = item.personalAccount
        addressBox.text =
            address.city + " " + address.street + " " + address.unit + " " + address.number
        counterModelBox.text = item.deviceProfileName
        serNBox.text = item.serN
        lastSeenAtBox.text = convertFormatDateFromIso(item.lastSeenAt)
        tvReadingTime.text = "С " +
                convertFormatDateFromIso(item.lastSeenAt) + " До " +
                convertFormatDateFromIso(item.firstSeenAt)
        beginningPeriod.text = item.firstPeriodCurrency
        endPeriod.text = item.lastPeriodCurrency
        forPeriod.text = item.allPeriodCurrency
        val statusProgressBar = messageBoxView.findViewById(R.id.pbStatusProgressBar) as ProgressBar
        val statusCheck = messageBoxView.findViewById(R.id.statusCheck) as ConstraintLayout
        val device = messageBoxView.findViewById(R.id.tvDevice) as TextView
        val status = messageBoxView.findViewById(R.id.tvStatus) as TextView
        statusCheck.visibility = GONE
        statusProgressBar.visibility = GONE
        btnStatusCheck.setOnClickListener() {
            viewModel.getDeviceStatus(item.devEui)
            btnStatusCheck.visibility = GONE
            statusProgressBar.visibility = VISIBLE
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.mainState.collect { state ->
                when (state) {
                    is MainState.Status -> {
                        device.text = state.response.device
                        status.text = when (state.response.status) {
                            true -> "Включен"
                            false -> "Выключен"
                        }
                        statusProgressBar.visibility = GONE
                        statusCheck.visibility = VISIBLE
                    }
                    else -> Unit
                }
            }
        }
        status.setOnClickListener() {
            // TODO
            if (status.text == "Включен") {
                status.text = "Выключен"
            } else {
                status.text = "Включен"
            }
        }
        messageBoxView.setOnClickListener() {
            messageBoxInstance.dismiss()
        }
    }

    lateinit var backToast: Toast

    private val backPressHandler = object : BackPressHandler {
        override fun onBackPressed(): Boolean {
            backToast = Toast.makeText(activity, R.string.press_back_leave_app, Toast.LENGTH_SHORT)
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel()
                exitProcess(0)
            } else {
                backToast.show()
            }
            backPressedTime = System.currentTimeMillis()
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
    ) = FragmentMainBinding.inflate(inflater, container, false)

}