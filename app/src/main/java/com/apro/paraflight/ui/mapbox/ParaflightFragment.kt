package com.apro.paraflight.ui.mapbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.apro.paraflight.R

class ParaflightFragment : Fragment() {

  private lateinit var slideshowViewMode: ParaFlightViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
//    slideshowViewModel =
//    ViewModelProviders.of(this).get(ParaFlightViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_paraflight, container, false)
//    val textView: TextView = root.findViewById(R.id.text_home)
//    slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
//      textView.text = it
//    })
    return root
  }
}