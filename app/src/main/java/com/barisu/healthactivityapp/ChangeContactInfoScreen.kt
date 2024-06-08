package com.barisu.healthactivityapp

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun ChangeContactInfoScreen(navigateToMainMenu: () -> Unit)
{
    val changeContactInfoViewModel: ChangeContactInfoViewModel = viewModel()
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("5333333333") }
    var homeAddress by remember { mutableStateOf("Kalkanlı Güzelyurt") }
    var errorText by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Mr.", "Mrs.", "Other")

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Name text field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Surname text field
        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it },
            label = { Text("Surname") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopEnd)
                .clickable { expanded = !expanded }
        ) {
            Row(modifier = Modifier.fillMaxWidth().border(1.dp,Color.Gray,shape = RoundedCornerShape(10)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {


                Text(modifier=Modifier.padding(16.dp),text = "Title : ${title}")
                
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "More"
                    )
                }
            }
            

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { option ->
                    DropdownMenuItem(text = { Text(text = option) }, onClick = {
                        title = option
                        expanded = false
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Telephone text field
        OutlinedTextField(
            value = telephone,
            onValueChange = { telephone = it },
            label = { Text("Telephone") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Home address text field
        OutlinedTextField(
            value = homeAddress,
            onValueChange = { homeAddress = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Change password button
        Button(
            // On click, send data to the viewmodel to check if passwords match.
            onClick = {
                changeContactInfoViewModel.changeContactInfo(name,surname,title,telephone,homeAddress,context,
                    onSuccess = {
                        // On success navigate to main menu.
                        navigateToMainMenu()
                    },
                    onError = {
                        // On error, set error text, TODO send custom text from viewmodel
                        errorText = "Passwords don't match"
                    }
                )


            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
        ) {
            Text("Change Contact Info")
        }
        Button(
            onClick = {
            changeContactInfoViewModel.closePage(context)
            navigateToMainMenu()
                      },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))) {
            Text(text = "Return to Main Page")
        }

        // Display error message if any
        if (errorText.isNotBlank()) {
            Text(text = errorText, color = Color.Red)
        }
    }
}

