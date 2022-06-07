package ru.magomedcoder.askue.ui.archive.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.magomedcoder.askue.ui.base.BaseDiffUtilItemCallback
import ru.magomedcoder.askue.ui.base.DisplayableItem

@RequiresApi(Build.VERSION_CODES.O)
class ArchiveAdapter :
    AsyncListDifferDelegationAdapter<DisplayableItem>(BaseDiffUtilItemCallback()) {

    init {
        delegatesManager.addDelegate(
            ArchiveAdapterDelegate.mainAdapterDelegate()
        )
    }

}