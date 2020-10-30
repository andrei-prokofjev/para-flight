package com.apro.paraflight.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidResourceProvider @Inject constructor(
  private val context: Context
) : ResourceProvider {

  override fun getString(@StringRes id: Int): String = context.getString(id)

  override fun getString(@StringRes id: Int, vararg args: Any?): String = context.getString(id, *args)

  override fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg args: Any?): String =
    context.resources.getQuantityString(id, quantity, *args)

  override fun getDrawable(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(context, id)

}