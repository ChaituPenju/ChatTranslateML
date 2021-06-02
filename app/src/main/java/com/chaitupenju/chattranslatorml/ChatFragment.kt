package com.chaitupenju.chattranslatorml

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.chaitupenju.chattranslatorml.databinding.FragmentChatBinding
import com.chaitupenju.chattranslatorml.models.Chat
import com.chaitupenju.chattranslatorml.models.Message
import com.chaitupenju.chattranslatorml.models.MessageType
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChatFragment : Fragment() {

    val TAG = HomeFragment::class.java.simpleName

    lateinit var mSocket: Socket
    lateinit var userName: String
    lateinit var roomName: String

    val gson: Gson = Gson()
    val chatList = ArrayList<Message>()

    lateinit var chatAdapter: ChatAdapter

    private lateinit var chatBinding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        chatBinding = FragmentChatBinding.inflate(inflater, container, false)
        return chatBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatAdapter = ChatAdapter(chatMessages = chatList)
        chatBinding.rvChat.adapter = chatAdapter

        arguments?.let {
            userName = it.getString(Constants.CHAT_NAME_KEY)!!
            roomName = it.getString(Constants.CHAT_ROOM_KEY)!!
        }

        chatBinding.btnSendMessage.setOnClickListener {
            sendMessage()
        }

        initializeSocketIo()
    }

    override fun onDestroy() {
        super.onDestroy()

        mSocket.disconnect()
    }

    var onConnect = Emitter.Listener {
        mSocket.emit("subscribe", gson.toJson(Chat(userName = userName, roomName = roomName)))
    }

    var onNewUser = Emitter.Listener {
        val name = it[0] as String //This pass the userName!
        val chat = Message(Chat(name, "", roomName), MessageType.USER_JOIN)
        addItemToRecyclerView(chat)
        Log.d(TAG, "on New User triggered.")
    }

    var onUserLeft = Emitter.Listener {
        val leftUserName = it[0] as String
        val chat = Message(Chat(leftUserName, "", ""), MessageType.USER_LEAVE)
        addItemToRecyclerView(chat)
    }

    var onUpdateChat = Emitter.Listener {
        val chat = Message(gson.fromJson(it[0].toString(), Chat::class.java), MessageType.CHAT_OTHER)
        addItemToRecyclerView(chat)
    }

    private fun initializeSocketIo() {
        IO.socket("http://10.0.2.2:3000").runCatching {
            mSocket = this@runCatching
        }.onFailure {
            Log.e(TAG, "Some Error Occured ${it.localizedMessage}")
        }

        mSocket.connect()

        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on("newUserToChatRoom", onNewUser)
        mSocket.on("updateChat", onUpdateChat)
        mSocket.on("userLeftChatRoom", onUserLeft)
    }

    private fun addItemToRecyclerView(message: Message) {
        requireActivity().runOnUiThread {
            chatList.add(message)
            chatAdapter.notifyItemInserted(chatList.size)
            chatBinding.etInstantMessage.setText("")
            chatBinding.rvChat.scrollToPosition(chatList.size - 1) //move focus on last message
        }
    }

    private fun sendMessage() {
        val instantMessage = chatBinding.etInstantMessage.text.toString()
        val sendData = Chat(userName, instantMessage, roomName)
        val jsonData = gson.toJson(sendData)
        mSocket.emit("newMessage", jsonData)

        val message = Message(Chat(userName, instantMessage, roomName), MessageType.CHAT_MINE)
        addItemToRecyclerView(message)
    }
}