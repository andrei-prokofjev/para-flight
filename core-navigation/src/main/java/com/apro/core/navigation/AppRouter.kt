package com.apro.core.navigation

import androidx.fragment.app.DialogFragment
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Router


class AppRouter : Router() {

  fun openModalDialogFragment(fragment: DialogFragment) {
    executeCommands(ModalDialog(fragment))
  }

}

data class ModalDialog(val fragment: DialogFragment) : Command
