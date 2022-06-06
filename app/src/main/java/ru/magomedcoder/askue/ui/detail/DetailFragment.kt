package ru.magomedcoder.askue.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import ru.magomedcoder.askue.databinding.DetailElectronicCounterBinding
import ru.magomedcoder.askue.ui.base.BaseFragment

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailElectronicCounterBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val devEui = arguments?.getString("devEui")
        val serN = arguments?.getString("serN")
        binding.apply {
            tvSerN.text = serN
            tvDevEui.text = devEui
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DetailElectronicCounterBinding.inflate(inflater, container, false)

}