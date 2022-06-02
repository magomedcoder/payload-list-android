package ru.magomedcoder.askue.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.magomedcoder.askue.databinding.ItemElectronicCounterBinding
import ru.magomedcoder.askue.domain.model.ElectronicCounter
import ru.magomedcoder.askue.ui.base.DisplayableItem

object ElectronicCounterAdapterDelegate {

    fun mainAdapterDelegate() =
        adapterDelegateViewBinding<ElectronicCounter, DisplayableItem, ItemElectronicCounterBinding>(
            { layoutInflater, parent ->
                ItemElectronicCounterBinding.inflate(layoutInflater, parent, false)
            }) {
            bind {
                binding.tvDevEui.text = item.devEui
                binding.tvSerN.text = item.SerN
            }
        }

}