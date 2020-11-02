package com.apro.paraflight.ui.flight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.apro.core.ui.BaseBottomSheetDialogFragment
import com.apro.core.util.toTimeFormat
import com.apro.paraflight.databinding.FragmentFlightSummaryBinding

class FlightSummaryBottomSheetDialogFragment : BaseBottomSheetDialogFragment() {


  private lateinit var binding: FragmentFlightSummaryBinding

  private var dist = 0
  private var duration = 0L
  private var averageSpeed = 0f


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentFlightSummaryBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(binding) {
      totalDistanceTextView.text = "Total distance: $dist m"
      durationTextView.text = "Flight duration: ${duration.toTimeFormat()} "
      averageSpeedTextView.text = "Average flight speed: $averageSpeed m/s"
    }
  }

  companion object {
    fun create(dist: Int, duration: Long, averageSpeed: Float) = FlightSummaryBottomSheetDialogFragment().apply {
      this.dist = dist
      this.duration = duration
      this.averageSpeed = averageSpeed
    }
  }


}