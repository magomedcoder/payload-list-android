package ru.magomedcoder.askue.ui.archive.adapter

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.magomedcoder.askue.ui.base.BaseDiffUtilItemCallback
import ru.magomedcoder.askue.ui.base.DisplayableItem

class ArchiveAdapter :
    AsyncListDifferDelegationAdapter<DisplayableItem>(BaseDiffUtilItemCallback()) {

    init {
        delegatesManager.addDelegate(
            ArchiveAdapterDelegate.mainAdapterDelegate()
        )
    }

}