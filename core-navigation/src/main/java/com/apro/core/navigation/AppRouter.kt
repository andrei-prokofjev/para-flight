package com.apro.core.navigation

import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Router
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AppRouter : Router() {

  fun openModalBottomSheet(fragment: BottomSheetDialogFragment) {
    executeCommands(ModalDialog(fragment))
  }

  fun openPersistentBottomSheet() {
    executeCommands(PersistentDialog())
  }

}

data class ModalDialog(val fragment: BottomSheetDialogFragment) : Command

class PersistentDialog : Command