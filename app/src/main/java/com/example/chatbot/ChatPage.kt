package com.example.chatbot

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatbot.ui.theme.ColorModel
import com.example.chatbot.ui.theme.ColorUser

@Composable
fun ChatPage(
    modifier : Modifier = Modifier,
    viewModel: ChatViewModel
) {
    Column(
        modifier = modifier
    ){
        AppHeader()
        MessageList(modifier = Modifier.weight(1f),messageList = viewModel.messageList)
        MessageInput(onMessageSend = {
            viewModel.sendMessage(it)
        })
    }
}

@Composable
fun MessageList(
    modifier : Modifier = Modifier,
    messageList : List<MessageModel>
) {

    Log.i("ChatPage" , "$messageList")
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ){
        items(messageList.reversed()){
            Message(message = it)
        }
    }
}

@Composable
fun Message(message : MessageModel){
    val isModel = message.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Box{
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(
                        if (isModel) ColorModel else ColorUser
                    )
                    .padding(16.dp)
            ) {
                SelectionContainer {
                    Text(
                        text = message.message,
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                }
            }
        }
    }
}



@Composable
fun MessageInput(onMessageSend : (String) -> Unit) {
    var message by remember {
        mutableStateOf("")
    }

    Row(
       modifier = Modifier
           .fillMaxWidth()
           .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = {
                message = it
            },
        )

        IconButton(onClick = {
            onMessageSend(message)
            message = ""
        } , modifier = Modifier.weight(1f)) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "Message send..")
        }
    }
}

@Composable
fun AppHeader(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ){
        Text(
            text = "Chat Bot",
            modifier = Modifier.padding(16.dp),
            fontSize = 22.sp,
            color = Color.White,
            fontStyle = FontStyle.Italic,
        )
    }
}