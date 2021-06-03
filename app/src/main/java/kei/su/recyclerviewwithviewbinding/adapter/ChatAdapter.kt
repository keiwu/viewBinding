package kei.su.recyclerviewwithviewbinding. adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kei.su.recyclerviewwithviewbinding.databinding.ChatConnectionFailBinding
import kei.su.recyclerviewwithviewbinding.databinding.ChatHeaderViewBinding
import kei.su.recyclerviewwithviewbinding.databinding.ChatRowViewBinding
import java.text.SimpleDateFormat
import java.util.*

/*
    References:
    https://androidwave.com/android-recyclerview-example-best-practices/
    https://stackoverflow.com/questions/60423596/how-to-use-viewbinding-in-a-recyclerview-adapter
    https://gist.github.com/fangbrian/8a79b7ee64ad3bd15c48509adcf25cef
    https://developer.android.com/topic/libraries/view-binding
    https://developer.android.com/topic/libraries/data-binding/expressions

 */

class ChatAdapter(ids: MutableList<String>, db: DatabaseManager, cb: Callback): RecyclerView.Adapter<BaseViewHolder>() {
    private val CONNECTION_ROW = 0
    private val CONNECTION_HEADER = 1
    private val CONNECTION_FAIL = 2

    var profileIds: MutableList<String> = ids
    var database: DatabaseManager = db
    var callBack: Callback = cb

    /*
        ChatViewHolder for displaying the chat conversation
        ChatHeaderViewHolder for displaying the date/time of the conversation
        ChatRetryViewHolder for displaying the view with retry button
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType){
            CONNECTION_ROW -> ChatViewHolder(
                ChatRowViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            CONNECTION_HEADER -> ChatHeaderViewHolder(
                ChatHeaderViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else->ChatRetryViewHolder(
                ChatConnectionFailBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
       holder.onBind(position, database.getProfile(position))
    }

    override fun getItemCount(): Int {
        return profileIds.size
    }

    override fun getItemViewType(position: Int): Int {
        if (profileIds != null && profileIds.size > 1) {
            CONNECTION_ROW
        } else if (profileIds != null && profileIds.size == 1) {
            CONNECTION_HEADER
        }

        return CONNECTION_FAIL


    }

    /*
        connection fail retry callback
     */
    interface Callback{
        fun onConnectionFailRetryClick()
    }


    /*
        Use of ViewBinding
        Use of Glide to load/display image
     */
    class ChatViewHolder(var viewBinding: ChatRowViewBinding): BaseViewHolder(viewBinding.root) {

        override fun onBind(position: Int, profile: Profile) {
            with(viewBinding) {
                if (profile.photoUrl != null) {
                    Glide.with(viewBinding.root).load(profile.photoUrl)
                        .into(avatarImage)
                }

                if (profile.name != null) {
                    name.text = profile.name
                }

                if (profile.lastMessageSent != null){
                    lastMessageSent.text = profile.lastMessageSent
                }
            }
        }
    }


    class ChatHeaderViewHolder(var viewBinding: ChatHeaderViewBinding): BaseViewHolder(viewBinding.root) {
        override fun onBind(position: Int, profile: Profile) {
            val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            val currentDateandTime: String = sdf.format(Date())

            viewBinding.header.text= currentDateandTime
        }
    }

    inner class ChatRetryViewHolder(var viewBinding: ChatConnectionFailBinding): BaseViewHolder(
        viewBinding.root
    ) {
        override fun onBind(position: Int, profile: Profile) {
            viewBinding.retryBtn.setOnClickListener {
                callBack.onConnectionFailRetryClick()
            }
        }
    }


}