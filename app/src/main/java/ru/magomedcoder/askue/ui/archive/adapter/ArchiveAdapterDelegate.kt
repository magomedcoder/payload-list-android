package ru.magomedcoder.askue.ui.archive.adapter

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.magomedcoder.askue.databinding.ItemElectronicEventBinding
import ru.magomedcoder.askue.domain.model.ElectronicArchive
import ru.magomedcoder.askue.ui.base.DisplayableItem

object ArchiveAdapterDelegate {

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun mainAdapterDelegate() =
        adapterDelegateViewBinding<ElectronicArchive, DisplayableItem, ItemElectronicEventBinding>(
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