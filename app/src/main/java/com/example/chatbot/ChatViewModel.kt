package com.example.chatbot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {

    private val generativeModel : GenerativeModel = GenerativeModel(
        apiKey = Constants.apiKey,
        modelName = "gemini-pro"
    )

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }


    fun sendMessage(message: String){
        viewModelScope.launch {
            val chat = generativeModel.startChat(
                history = messageList.map {
                    content(it.role) {text(it.message)}
                }.toList()
            )

            messageList.add(MessageModel(message , "user"))
            messageList.add(MessageModel("typing.." , "model"))

            val response = chat.sendMessage(message)
            messageList.removeLast()
            messageList.add(MessageModel(response.text.toString() , "model"))
            Log.i("ChatViewModel" , "$messageList")
            Log.i("ChatViewModel",response.text.toString())
        }
    }
}