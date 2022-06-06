package ru.magomedcoder.askue.ui.main.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.magomedcoder.askue.domain.model.ElectronicCounter
import ru.magomedcoder.askue.ui.base.BaseDiffUtilItemCallback
import ru.magomedcoder.askue.ui.base.DisplayableItem

class ElectronicCounterAdapter(
    private val onGoToDetail: (ElectronicCounter) -> Unit
) : AsyncListDifferDelegationAdapter<DisplayableItem>(BaseDiffUtilItemCallback()) {

    init {
        delegatesManager.addDelegate(
            ElectronicCounterAdapterDelegate.mainAdapterDelegate(onGoToDetail)
        )
    }

}