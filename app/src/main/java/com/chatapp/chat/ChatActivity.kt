package com.chatapp.chat

import android.hardware.TriggerEvent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatapp.Constants
import com.chatapp.R
import com.chatapp.model.Room
import com.chatapp.ui.theme.ChatAppTheme
import com.chatapp.widget.ChatTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chatapp.model.DataUtils
import com.chatapp.model.Message
import com.chatapp.ui.theme.cyan
import com.chatapp.ui.theme.lightGray
import com.chatapp.widget.ChatMessageTextField
import com.chatapp.widget.SendMessageButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatAppTheme {
                val room = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(Constants.ROOM_INTENT_EXTRA, Room::class.java)
                } else {
                    intent.getParcelableExtra<Room>(Constants.ROOM_INTENT_EXTRA)
                }
                ChatScreen(room!!) {
                    finish()
                }


            }
        }
    }
}


@Composable
fun ChatScreen(room: Room, viewModel: ChatViewModel = viewModel(), onFinish: () -> Unit) {
    LaunchedEffect(key1 = Unit) {
        viewModel.room = room

    }
    Scaffold(
        topBar = {
            ChatTopBar(title = room.roomName ?: "") {
                viewModel.navigateBack()

            }
        }
    ) { paddingValues ->
        paddingValues
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.bg), contentScale = ContentScale.FillBounds
                )

        ) {
           ChatMessageLazyColumn(modifier = Modifier
               .padding(36.dp)
               .fillMaxHeight(.85f)
               .padding(top = paddingValues.calculateTopPadding())
               .background(Color.White, RoundedCornerShape(20.dp))
               .border(1.dp, Color.Gray, RoundedCornerShape(20.dp)))
            ChatMessageBottomBar()



        }


    }

    TriggerEvent(events = viewModel.events.value) {
        onFinish()

    }

}

@Composable
fun ChatMessageLazyColumn(modifier: Modifier = Modifier, viewModel: ChatViewModel = viewModel()) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getMessages()

    }


        LazyColumn(modifier = modifier,reverseLayout = true) {
            items(viewModel.messagesList.size) { position ->
                if (viewModel.messagesList[position].senderId == DataUtils.appUser?.userId) {
                    SentMessageCard(message = viewModel.messagesList[position])
                } else {
                    ReceivedMessageCard(message = viewModel.messagesList[position])

                }

            }


        }


}
fun formatDate(date: Date): String {
    val simpleDateFormat=SimpleDateFormat("hh:mm a", Locale.getDefault())
    return simpleDateFormat.format(date)
}

@Composable
fun SentMessageCard(message: Message) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), horizontalArrangement = Arrangement.End) {
        message.date?.let {
            Text(text = formatDate(Date(it)), modifier = Modifier.align(Alignment.Bottom))
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = message.content ?: "",
            fontSize = 14.sp,
            modifier = Modifier
                .background(
                    cyan,
                    RoundedCornerShape(
                        topStart = 28.dp,
                        topEnd = 28.dp,
                        bottomStart = 28.dp,

                        )
                )
                .padding(8.dp)
                .padding(end = 8.dp)
        )

    }

}

@Preview
@Composable
private fun SentMessageCardPreview() {
    //SentMessageCard(message = Message(content = "Hello world ",date = System.currentTimeMillis()))
    ReceivedMessageCard(message = Message(content = "Hello world ",date = System.currentTimeMillis(), senderName = "Hossamoud"))
}

@Composable
fun ReceivedMessageCard(message: Message) {
    Column {
        Text(text = message.senderName?:"", modifier = Modifier.padding( top = 8.dp, start = 16.dp, bottom = 8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {


            Text(
                text = message.content ?: "",
                fontSize = 14.sp,
                modifier = Modifier
                    .background(
                        lightGray,
                        RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 28.dp,
                            bottomEnd = 28.dp,

                            )
                    )
                    .padding(8.dp)
                    .padding(end = 8.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            message.date?.let {
                Text(text = formatDate(Date(it)), modifier = Modifier.align(Alignment.Bottom))
            }


        }

    }
}

@Composable
fun ChatMessageBottomBar(viewModel: ChatViewModel = viewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        ChatMessageTextField(state = viewModel.messageState)
        Spacer(modifier = Modifier.padding(12.dp))
        SendMessageButton {
            viewModel.sendMessage()
        }

    }

}

@Composable
fun TriggerEvent(events: ChatViewEvents, onFinish: () -> Unit) {
    when (events) {
        ChatViewEvents.Idle -> {}
        ChatViewEvents.NavigateBack -> {
            onFinish()
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatScreenPreview() {
    ChatScreen(room = Room(), onFinish = {})

}