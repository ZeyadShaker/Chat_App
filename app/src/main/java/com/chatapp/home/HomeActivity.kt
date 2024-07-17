package com.chatapp.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatapp.R
import com.chatapp.ui.theme.cyan
import com.chatapp.widget.ChatTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chatapp.chat.ChatActivity
import com.chatapp.Constants
import com.chatapp.addRoom.AddRoomActivity
import com.chatapp.model.Category
import com.chatapp.model.Room
import com.chatapp.widget.LoadingDialog

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen()


            }

        }
    }



@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    Scaffold(
        topBar = {
            ChatTopBar(title = stringResource(R.string.chat_app))
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.navigateToAddRoom()
                },
                shape = CircleShape,
                containerColor = cyan, modifier = Modifier.size(70.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = stringResource(R.string.add_room)
                )

            }
        }
    ) { paddingValues ->
        paddingValues
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.bg),
                    contentScale = ContentScale.FillBounds
                )
                .padding(top = paddingValues.calculateTopPadding())
                .padding(top = 50.dp)
        )
        {
            RoomsLazyGrid()

        }

    }
    LoadingDialog(isLoading = viewModel.isLoading)

    TriggerEvent(events = viewModel.events.value)



}

@Composable
fun RoomsLazyGrid(viewModel: HomeViewModel= viewModel() ) {
    LaunchedEffect(Unit) {
        viewModel.getRooms()
    }
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(viewModel.roomsList.size){position ->
            RoomCard(room = viewModel.roomsList.get(position))

        }
        
    }
    
}

@Composable
fun RoomCard(room: Room,viewModel: HomeViewModel = viewModel()) {
    Card(
        modifier = Modifier
            .padding(28.dp)
            .fillMaxWidth()
            .clickable {
                viewModel.navigateToRoomChat(room)

            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
        , border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Spacer(modifier = Modifier.padding(14.dp))
   Image(
       painter =
       painterResource(id = Category.
       getCategoryById(room.categoryId?:Category.MUSIC).image?:R.drawable.music),
       contentDescription ="Room Image"
       ,modifier = Modifier
           .size(84.dp)
           .align(Alignment.CenterHorizontally) )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = room.roomName?:"",
            color = Color.Black,fontSize = 15.sp,
            fontWeight = FontWeight.Medium
            ,
             modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
    }

    
}



@Composable
fun TriggerEvent(
    viewModel: HomeViewModel = viewModel(),
    events: HomeViewEvents
) {
    val context = LocalContext.current
    when (events) {
        HomeViewEvents.Idle -> {}
        HomeViewEvents.NavigateToAddRoom -> {
            val intent = Intent(context, AddRoomActivity::class.java)
            context.startActivity(intent)
            viewModel.resetToIdle()

        }

        is HomeViewEvents.NavigateToRoomChat -> {
            val room=events.room
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(Constants.ROOM_INTENT_EXTRA,room)
            context.startActivity(intent)
            viewModel.resetToIdle()
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
@Preview()
@Composable
private fun RoomCardPreview() {
    RoomCard(room = Room(Category.MUSIC,"Room 1",Category.MUSIC))

}
