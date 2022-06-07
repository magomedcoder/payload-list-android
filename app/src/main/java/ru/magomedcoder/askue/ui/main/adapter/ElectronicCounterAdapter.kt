package ru.magomedcoder.askue.ui.main.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.magomedcoder.askue.domain.model.ElectronicCounter
import ru.magomedcoder.askue.ui.base.BaseDiffUtilItemCallback
import ru.magomedcoder.askue.ui.base.DisplayableItem

@RequiresApi(Build.VERSION_CODES.O)
class ElectronicCounterAdapter(onGoToDetail: (ElectronicCounter) -> Unit) :
    AsyncListDifferDelegationAdapter<DisplayableItem>(BaseDiffUtilItemCallback()) {

    init {
        delegatesManager.addDelegate(
            ElectronicCounterAdapterDelegate.mainAdapterDelegate(onGoToDetail)
        )
    }

}