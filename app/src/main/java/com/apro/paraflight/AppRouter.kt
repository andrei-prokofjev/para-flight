package com.apro.paraflight

import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Router

class AppRouter : Router() {

  fun openModalBottomSheet() {
    executeCommands(BottomSheet())
  }

}

class BottomSheet : Command