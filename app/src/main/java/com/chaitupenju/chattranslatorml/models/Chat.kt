package com.chaitupenju.chattranslatorml.models

data class Chat(
    val userName: String,
    val messageContent: String? = null,
    val roomName: String
) {
    override fun toString(): String {
        return """
            {
                userName: $userName,
                roomName: $roomName,
                messageContent: $messageContent
            }
        """.trimIndent()
    }
}