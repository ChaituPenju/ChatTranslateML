package com.chaitupenju.chattranslatorml

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaitupenju.chattranslatorml.databinding.LayoutChatItemBinding
import com.chaitupenju.chattranslatorml.models.Message
import com.chaitupenju.chattranslatorml.models.MessageType

class ChatAdapter(private val chatMessages: List<Message>): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(private val chatItem: LayoutChatItemBinding) : RecyclerView.ViewHolder(chatItem.root) {

        fun bind(message: Message) {

            when (message.messageType) {
                MessageType.CHAT_MINE -> chatItem.tvChatMessage.gravity = Gravity.END
                MessageType.CHAT_OTHER -> chatItem.tvChatMessage.gravity = Gravity.START
                MessageType.USER_JOIN, MessageType.USER_LEAVE -> chatItem.tvChatMessage.gravity = Gravity.CENTER_HORIZONTAL
            }

            chatItem.message = message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatViewHolder(
        LayoutChatItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
    )

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chatMessages[position])
    }

    override fun getItemCount() = chatMessages.size
}