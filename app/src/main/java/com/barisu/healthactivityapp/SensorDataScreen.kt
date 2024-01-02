package com.barisu.healthactivityapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


// Item class
data class SensorGroup(
    val id:Int,
    var name: String,
    var isEditing: Boolean = false,
    var isExpanded: Boolean = false)

@Composable
fun SensorDataScreen(navigateToMainMenu:() -> Unit){
    // Variable to hold item list
    val tempList = listOf<SensorGroup>(SensorGroup(1,"Test1"),
        SensorGroup(2,"Test2"),
        SensorGroup(3,"Test3"))
    var sensorGroups by remember{ mutableStateOf(tempList) }
    var showDialog by remember{ mutableStateOf(false)}
    var groupName by remember{ mutableStateOf("")}
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){

        // Item list display
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .weight(1f)){
            items(sensorGroups){
                    item ->
                if (item.isEditing){
                    SensorGroupEditor(item = item, onEditComplete = {
                            editedName ->
                        sensorGroups = sensorGroups.map{it.copy(isEditing = false)}
                        val editedItem = sensorGroups.find{ it.id == item.id}
                        editedItem?.let{
                            it.name = editedName
                        }
                    } )
                }
                else{
                    SensorGroupItem(item = item,
                        onEditClick = {
                            // Finding the item that will be edited and changing the bool to true
                            sensorGroups = sensorGroups.map{it.copy(isEditing = it.id == item.id)}
                        },
                        onDeleteClick = {
                            sensorGroups = sensorGroups-item
                        },
                        onExpandClick = {
                            if(item.isExpanded==true){
                                item.isExpanded = false
                            }
                            else{
                                item.isExpanded = true
                            }
                        })
                }
            }
        }
        Row(modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround) {
            // Add item button
            Button(onClick = {showDialog = true},
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))) {
                Text(text = "Add Sensor Group", maxLines = 1)
            }
            Button(onClick = {navigateToMainMenu()},
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))){
                Text(text = "Back to Main Page", maxLines = 1)
            }
        }
        
    }

    if(showDialog){
        AlertDialog(onDismissRequest = { showDialog = false },
            confirmButton = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Button(onClick = {
                        if(groupName.isNotBlank()){
                            var newItem = SensorGroup(
                                id= sensorGroups.size+1,
                                name = groupName,
                            )
                            sensorGroups = sensorGroups + newItem
                            showDialog = false
                            groupName = ""
                        }
                    }) {
                        Text(text = "Add Sensor Group")
                    }
                    Button(onClick = { showDialog = false }) {
                        Text(text = "Cancel")
                    }
                }
            },
            title = { Text(text = "Add Sensor Group")},
            text = {
                Column {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Group Name")
                        OutlinedTextField(
                            value = groupName,
                            onValueChange = { groupName = it },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun SensorGroupItem(
    item: SensorGroup,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onExpandClick: () -> Unit,
){
    var packetRate by remember { mutableStateOf(0)}
    var packetReceived by remember { mutableStateOf(0)}
    Row (modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .border(
            border = BorderStroke(2.dp, Color(0XFF018786)),
            shape = RoundedCornerShape(20)
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        Text(text = "Name: ${item.name}", modifier = Modifier
            .padding(8.dp)
            .weight(1f))

        if(item.isExpanded){
            Column {
                Text(text = "GRAPH GOES HERE")

                Text(text = "Packet Rate: $packetRate")
                Text(text = "Last Packet Received $packetReceived ms ago")
            }
        }

        Row(modifier = Modifier.padding(8.dp)){
            IconButton(onClick = onEditClick){
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
            IconButton(onClick = onDeleteClick){
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
            /*IconButton(onClick = onExpandClick){
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }*/
        }
    }

}

@Composable
fun SensorGroupEditor(item: SensorGroup, onEditComplete: (String) -> Unit){
    var editedName by remember { mutableStateOf(item.name)}
    var isEditing by remember { mutableStateOf(item.isEditing)}

    Row (modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly)
    {
        Column {
            BasicTextField(
                value= editedName,
                onValueChange = {editedName = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
        }
        Button(onClick = {
            isEditing = false
            onEditComplete(editedName)
        }) {
            Text("Save")
        }
    }
}