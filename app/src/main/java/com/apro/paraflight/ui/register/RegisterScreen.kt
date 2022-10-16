package com.apro.paraflight.ui.register

import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.apro.paraflight.design.ParaflightTheme
import com.apro.paraflight.ui.NavRoute
import com.apro.paraflight.ui.popUpTo

@Composable
fun RegisterScreen(
  navController: NavHostController,
  viewModel: RegisterViewModel,
) {
  val context = LocalContext.current

  viewModel.errorState?.let {
    LaunchedEffect(Unit) {
      Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }
  }
  viewModel.navigationState?.let {
    LaunchedEffect(Unit) {
      navController.navigate(it) {
        popUpTo(NavRoute.Register) { inclusive = true }
      }
    }
  }

  Surface(modifier = Modifier.fillMaxSize()) {
    RegisterScreen { viewModel.registerClicked(it.first, it.second) }
  }
}

@Composable
private fun RegisterScreen(
  registerClick: (Pair<String, String>) -> Unit
) {
  Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    var enteredUserName by remember { mutableStateOf("") }
    val userNameInteractionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
      modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
      value = enteredUserName,
      label = { Text("User name") },
      onValueChange = { enteredUserName = it },
      placeholder = {
        Text(
          text = "User name",
          color = ParaflightTheme.colorScheme.onSurface.copy(alpha = .5f)
        )
      },
      maxLines = 1,
      interactionSource = userNameInteractionSource
    )
    var enteredPassword by remember { mutableStateOf("") }
    val passwordInteractionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
      modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
      value = enteredPassword,
      label = { Text("Password") },
      onValueChange = { enteredPassword = it },
      placeholder = {
        Text(
          text = "Password",
          color = ParaflightTheme.colorScheme.onSurface.copy(alpha = .5f)
        )
      },
      maxLines = 1,
      interactionSource = passwordInteractionSource
    )

    Button(
      modifier = Modifier.padding(top = 32.dp),
      onClick = { registerClick.invoke(enteredUserName to enteredPassword) }
    ) {
      Text(text = "Register")
    }
  }
}

@Composable
@Preview(showBackground = true)
private fun RegisterScreenPreview() {
  ParaflightTheme {
    RegisterScreen {}
  }
}