package com.barisu.healthactivityapp

import LoginViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun LoginScreen(onLoginSuccess: () -> Unit,
                navigateToMain: () -> Unit,
                navigateToUser: () -> Unit
                ) {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel()
    var ipAddress by remember { mutableStateOf("192.168.137.26") }
    var password by remember { mutableStateOf("123") }
    var adminMode by remember { mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ip address text field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = ipAddress,
            onValueChange = { ipAddress = it },
            label = { Text("Ip Address") }

        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password text field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()

        )

        Spacer(modifier = Modifier.height(16.dp))

        // Login button
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                //navigateToUser()
                // On click call viewmodel login method and send the ipAddress and password
                      loginViewModel.login(ipAddress, password, adminMode, context, onSuccess = {
                          onLoginSuccess()
                          if(adminMode){
                              navigateToMain()
                          }
                          else{
                              navigateToUser()
                          }
                      }, onFailure = {
                          //TODO display to user that the login failed with the correct error text.
                          println("Login Failed")
                      })
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))

        ) {
            Text("Login")
        }

        // User mode Switch
        Row(verticalAlignment = Alignment.CenterVertically){
            Text(text = "Admin Mode: ")
            Switch(checked = adminMode, colors = SwitchDefaults.colors(
                checkedTrackColor = Color(0xFF2196F3)
            ), onCheckedChange = {
                adminMode = it
            })
        }

    }
}
