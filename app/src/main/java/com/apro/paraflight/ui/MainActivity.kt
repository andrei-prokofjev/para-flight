package com.apro.paraflight.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apro.paraflight.design.ParaflightTheme
import com.apro.paraflight.ui.dashboard.DashboardScreen
import com.apro.paraflight.ui.help.HelpScreen
import com.apro.paraflight.ui.login.LoginScreen
import com.apro.paraflight.ui.register.RegisterScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    val splashScreen = installSplashScreen()
    super.onCreate(savedInstanceState)
    splashScreen.setKeepOnScreenCondition { true }

    setContent {
      ParaflightTheme {
        splashScreen.setKeepOnScreenCondition { false }
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = NavRoute.Register.route) {
          composable(NavRoute.Register.pattern) {
            RegisterScreen(navController = navController, viewModel = hiltViewModel())
          }
          composable(NavRoute.Login.pattern) {
            LoginScreen(navController = navController)
          }
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



