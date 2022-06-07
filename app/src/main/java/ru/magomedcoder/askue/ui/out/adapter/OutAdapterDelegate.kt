package ru.magomedcoder.askue.ui.out.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.magomedcoder.askue.databinding.ItemElectronicCounterBinding
import ru.magomedcoder.askue.domain.model.ElectronicOut
import ru.magomedcoder.askue.ui.base.DisplayableItem
import ru.magomedcoder.askue.utils.convertFormatDateFromIso

object OutAdapterDelegate {

    @RequiresApi(Build.VERSION_CODES.O)
    fun mainAdapterDelegate() =
        adapterDelegateViewBinding<ElectronicOut, DisplayableItem, ItemElectronicCounterBinding>(
            { layoutInflater, parent ->
                ItemElectronicCounterBinding.inflate(layoutInflater, parent, false)
            }) {
            bind {
                val address = item.placeAddress
                binding.apply {
                    tvAddress.text =
                        address.city + " " + address.street + " " + address.unit + " " + address.number

                    tvPersonalAccount.text = item.personalAccount
                    tvSerN.text = item.serN
                    tvLastSeenAt.text = convertFormatDateFromIso(item.lastSeenAt)
                    tvAddress.text =
                        address.city + " " + address.street + " " + address.unit + " " + address.number
                }
            }
        }

}