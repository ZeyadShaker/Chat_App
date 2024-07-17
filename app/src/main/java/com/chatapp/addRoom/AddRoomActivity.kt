package com.chatapp.addRoom

import android.hardware.TriggerEvent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatapp.R
import com.chatapp.ui.theme.ChatAppTheme
import com.chatapp.ui.theme.black2
import com.chatapp.widget.ChatAuthTextField
import com.chatapp.widget.ChatTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chatapp.widget.AddButton
import com.chatapp.widget.LoadingDialog

class AddRoomActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AddRoomScreen() {
                finish()
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomScreen(viewModel: AddRoomViewModel = viewModel(), onFinish: () -> Unit) {
    Scaffold(
        topBar = {
            ChatTopBar(
                title = stringResource(id = R.string.chat_app),
                onNavigationClick = { onFinish() })
        }
    ) { paddingValues ->
        paddingValues
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.bg),
                    contentScale = ContentScale.FillBounds
                )
                .padding(top = paddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(Color.White)
            ) {
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = stringResource(R.string.create_new_room),
                    color = black2,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.add_new_room),
                    contentDescription = "add new room",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight(.13f)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.padding(16.dp))
                ChatAuthTextField(
                    state = viewModel.roomNameState,
                    errorState = viewModel.roomNameErrorState.value,
                    label = stringResource(R.string.enter_room_name)

                )
                Spacer(modifier = Modifier.padding(8.dp))
                CategoryDropdown()






                Spacer(modifier = Modifier.padding(8.dp))
                ChatAuthTextField(
                    state = viewModel.roomDescriptionState,
                    errorState = viewModel.roomDescriptionErrorState.value,
                    label = stringResource(R.string.enter_room_description)

                )
                Spacer(modifier = Modifier.padding(28.dp))
                AddButton(title = stringResource(id = R.string.create),
                    modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    viewModel.addRoom()
                    
                    
                }

            }
        }


    }
    LoadingDialog(isLoading = viewModel.isLoading)
    TriggerEvent(events = viewModel.events.value, viewModel = viewModel) {
        onFinish()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(viewModel: AddRoomViewModel = viewModel(),modifier: Modifier = Modifier) {
    ExposedDropdownMenuBox(expanded = viewModel.isExpandedState.value,
        onExpandedChange = {
            viewModel.isExpandedState.value = !viewModel.isExpandedState.value
        }
    ) {
        OutlinedTextField(
            value = viewModel.selectedCategoryItem.value.name ?: "",
            onValueChange = {},
            readOnly = true,
            label = {
                Text(text = stringResource(R.string.enter_room_category))

            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.isExpandedState.value)
            }, leadingIcon = {
                Image(
                    painter = painterResource(
                        id = viewModel.selectedCategoryItem.value.image ?: R.drawable.movies
                    ),
                    contentDescription = "category image"
                    ,modifier = Modifier.size(24.dp)
                )
            }, modifier = modifier
                .padding(horizontal = 12.dp)
                .menuAnchor()

        )
        ExposedDropdownMenu(
            expanded = viewModel.isExpandedState.value,
            onDismissRequest = {
                viewModel.isExpandedState.value = false
            }, modifier = Modifier.background(Color.White)) {
            viewModel.categoriesList.forEach { Category ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(painter =
                            painterResource(id = Category.image?:R.drawable.movies),
                                contentDescription = "category image",
                                modifier = Modifier.size(24.dp))
                            Text(text = Category.name?:"",modifier = Modifier.padding(start = 12.dp))

                        }
                    }, onClick = { viewModel.selectedCategoryItem.value = Category
                    viewModel.isExpandedState.value = false}
                )
            }

        }
    }

}

@Composable
fun TriggerEvent(events: AddRoomViewEvents, viewModel: AddRoomViewModel = viewModel(),onFinish: () -> Unit) {
    when (events) {
        AddRoomViewEvents.Idle -> {}
        AddRoomViewEvents.NavigateBack -> {onFinish()}
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddRoomScreenPreview() {
    AddRoomScreen() {}

}
