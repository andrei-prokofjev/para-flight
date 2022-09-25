package com.apro.paraflight.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apro.paraflight.design.ParaflightTheme
import com.apro.paraflight.ui.dashboard.DashboardScreen
import com.apro.paraflight.ui.help.HelpScreen


class MainActivity : ComponentActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    val splashScreen = installSplashScreen()
    super.onCreate(savedInstanceState)
    splashScreen.setKeepOnScreenCondition { true }

    setContent {
      ParaflightTheme {
        splashScreen.setKeepOnScreenCondition { false }
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = NavRoute.Dashboard.route) {
          composable(NavRoute.Dashboard.pattern) {
            DashboardScreen(navController = navController)
          }
          composable(NavRoute.Help.pattern) {
            HelpScreen()
          }
        }
      }
    }
  }

}



