package com.apro.paraflight.ui

import androidx.navigation.*

sealed class NavRoute(
  val route: String,
  val pattern: String = route,
  val arguments: List<NamedNavArgument> = emptyList(),
  val deepLinks: List<NavDeepLink> = emptyList()
) {

  object Register : NavRoute("register")

  object Login : NavRoute("login")

  object Dashboard : NavRoute("dashboard")

  object Help : NavRoute("help")

}

fun NavController.navigate(route: NavRoute, builder: NavOptionsBuilder.() -> Unit = {}) {
  navigate(route.route, builder)
}

fun NavOptionsBuilder.popUpTo(route: NavRoute, popUpToBuilder: PopUpToBuilder.() -> Unit = {}) {
  popUpTo(route.route, popUpToBuilder)
}