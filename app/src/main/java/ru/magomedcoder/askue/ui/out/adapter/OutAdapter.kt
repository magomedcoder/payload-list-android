package ru.magomedcoder.askue.ui.out.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.magomedcoder.askue.ui.base.BaseDiffUtilItemCallback
import ru.magomedcoder.askue.ui.base.DisplayableItem

class OutAdapter : AsyncListDifferDelegationAdapter<DisplayableItem>(BaseDiffUtilItemCallback()) {

    init {
        delegatesManager.addDelegate(
            OutAdapterDelegate.mainAdapterDelegate()
        )
    }

}