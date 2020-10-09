package com.apro.paraflight


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val ft = supportFragmentManager.beginTransaction()
    ft.add(R.id.fragmentContainerLayout, MapboxFragment.create())
    ft.commit()
  }
}