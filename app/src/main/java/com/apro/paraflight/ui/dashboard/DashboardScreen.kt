package com.apro.paraflight.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.apro.paraflight.design.ParaflightTheme
import com.apro.paraflight.ui.NavRoute
import com.apro.paraflight.ui.navigate
import com.apro.paraflight.ui.popUpTo

@Composable
fun DashboardScreen(navController: NavController) {

  Surface(modifier = Modifier.fillMaxSize()) {

    Box(modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.BottomCenter) {
      Button(
        modifier = Modifier.padding(bottom = 16.dp),
        onClick = {
          navController.navigate(NavRoute.Help) {
            popUpTo(NavRoute.Dashboard)
          }
        }) {
        Text(text = "help")
      }
    }
  }
}

@Composable
@Preview
fun DashboardScreenPreview() {
  ParaflightTheme {
    DashboardScreen(rememberNavController())
  }
}