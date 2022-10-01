package com.apro.paraflight.ui.register

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.apro.paraflight.design.ParaflightTheme

@Composable
fun RegisterScreen(
  navController: NavHostController,
  viewModel: RegisterViewModel,
) {
  Surface(modifier = Modifier.fillMaxSize()) {
    RegisterScreen { viewModel.registerClicked(it) }
  }
}

@Composable
private fun RegisterScreen(
  registerClick: (String) -> Unit
) {
  Column(modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.SpaceEvenly,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    var enteredText by remember { mutableStateOf("") }

    val interactionSource = remember { MutableInteractionSource() }
//    val isFocused by interactionSource.collectIsFocusedAsState()

    OutlinedTextField(
      modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
      value = enteredText,
      label = { Text("Enter name") },
      onValueChange = { enteredText = it },
      placeholder = {
        Text(
          text = "Enter name",
          color = ParaflightTheme.colorScheme.onSurface.copy(alpha = .5f)
        )
      },
      maxLines = 1,
      interactionSource = interactionSource,
      keyboardActions = KeyboardActions(onDone = { registerClick.invoke(enteredText) }),
    )

    Button(
      onClick = { registerClick.invoke(enteredText) }
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