package ru.magomedcoder.askue.ui.event.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.magomedcoder.askue.ui.base.BaseDiffUtilItemCallback
import ru.magomedcoder.askue.ui.base.DisplayableItem


class EventAdapter : AsyncListDifferDelegationAdapter<DisplayableItem>(BaseDiffUtilItemCallback()) {

    init {
        delegatesManager.addDelegate(
            EventAdapterDelegate.mainAdapterDelegate()
        )
    }

}