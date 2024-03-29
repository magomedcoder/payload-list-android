package ru.magomedcoder.askue.ui.event.adapter

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.magomedcoder.askue.databinding.ItemElectronicEventBinding
import ru.magomedcoder.askue.domain.model.ElectronicEvent
import ru.magomedcoder.askue.ui.base.DisplayableItem

object EventAdapterDelegate {

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun mainAdapterDelegate() =
        adapterDelegateViewBinding<ElectronicEvent, DisplayableItem, ItemElectronicEventBinding>(
            { layoutInflater, parent ->
                ItemElectronicEventBinding.inflate(layoutInflater, parent, false)
            }) {
            bind {
                val address = item.placeAddress
                binding.apply {
                    tvDevice.text = item.modelOfDevice
                    tvDate.text = item.dateTime
                    tvAddress.text =
                        address.city + " " + address.street + " " + address.unit + " " + address.number
                }
            }
        }

}