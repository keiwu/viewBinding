package kei.su.recyclerviewwithviewbinding.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kei.su.recyclerviewwithviewbinding.databinding.ChatRowViewBinding


abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mCurrentPosition=0
        private set

    open fun onBind(position: Int, profile: Profile) {
        mCurrentPosition = position
    }

    open fun getCurrentPosition(): Int {
        return mCurrentPosition
    }
}