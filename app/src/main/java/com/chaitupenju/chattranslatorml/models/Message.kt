package com.chaitupenju.chattranslatorml.models

data class Message(
    val chat: Chat,
    var messageType: MessageType
) {
    override fun toString(): String {
        return chat.toString()
//        return """
//            {
//                userName: $userName,
//                roomName:$roomName,
//                messageContent:$messageContent
//            }
//        """.trimIndent()
    }
}