package com.example.chatroom.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatroom.data.Room
import com.example.chatroom.viewmodel.RoomViewModel

@Composable
fun ChatRoomListScreen(
    roomViewModel: RoomViewModel = viewModel(),
    onJoinClicked: (Room) -> Unit,
) {
    val rooms by roomViewModel.rooms.observeAsState(emptyList())
    var isShowDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Chat Rooms",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Display a list of chat rooms
        LazyColumn {
            items(rooms) { room ->
                RoomItem(
                    room = room,
                    onJoinClicked = onJoinClicked,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Button to create a new room
        Button(
            onClick = { isShowDialog = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Create Room")
        }
        if (isShowDialog) {
            AlertDialog(
                onDismissRequest = { isShowDialog = true },
                title = { Text(text = "Create a new room") },
                text = {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                if (name.isNotBlank()) {
                                    roomViewModel.createRoom(name)
                                    isShowDialog = false
                                }
                            },
                        ) {
                            Text(text = "Add")
                        }
                        Button(onClick = { isShowDialog = false }) {
                            Text(text = "Cancel")
                        }
                    }
                },
            )
        }
    }
}

@Composable
fun RoomItem(
    room: Room,
    onJoinClicked: (Room) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = room.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        )
        OutlinedButton(onClick = { onJoinClicked(room) }) {
            Text(text = "Join")
        }
    }
}

@Preview
@Composable
fun RoomItemPreview() {
    RoomItem(
        room = Room("id.com", "Name"),
        onJoinClicked = {},
    )
}

@Preview(showBackground = true)
@Composable
fun ChatRoomListPreview() {
    ChatRoomListScreen(
        roomViewModel = viewModel<RoomViewModel>(),
        onJoinClicked = {},
    )
}