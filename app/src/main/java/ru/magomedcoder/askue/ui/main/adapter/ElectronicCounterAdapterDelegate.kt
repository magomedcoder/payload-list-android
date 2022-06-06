package ru.magomedcoder.askue.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.magomedcoder.askue.databinding.ItemElectronicCounterBinding
import ru.magomedcoder.askue.domain.model.ElectronicCounter
import ru.magomedcoder.askue.ui.base.DisplayableItem
import ru.magomedcoder.askue.utils.getDateString

object ElectronicCounterAdapterDelegate {

    fun mainAdapterDelegate(onGoToDetail: (ElectronicCounter) -> Unit) =
        adapterDelegateViewBinding<ElectronicCounter, DisplayableItem, ItemElectronicCounterBinding>(
            { layoutInflater, parent ->
                ItemElectronicCounterBinding.inflate(layoutInflater, parent, false)
            }) {
            bind {
                val address = item.placeAddress
                binding.apply {
                    counterItem.setOnClickListener {
                        onGoToDetail(item)
                    }
                    tvPersonalAccount.text = item.personalAccount
                    tvSerN.text = item.serN
                    tvLastSeenAt.text = getDateString(item.lastSeenAt!!)
                    tvAddress.text =
                        address.city + " " + address.street + " " + address.unit + " " + address.number
                }
            }
        }

}