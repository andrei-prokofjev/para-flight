package com.apro.paraflight.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.apro.paraflight.R
import com.apro.paraflight.ui.NavRoute
import com.apro.paraflight.ui.navigate
import com.apro.paraflight.ui.popUpTo
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.logo.logo

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DashboardScreen(navController: NavController) {
  Surface(modifier = Modifier.fillMaxSize()) {
    val locationPermissionsState = rememberMultiplePermissionsState(
      listOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
      )
    )

    if (locationPermissionsState.allPermissionsGranted) {
      val style = remember { mutableStateOf(MapViewStyle.STREET) }
      val camera = remember {
        mutableStateOf(cameraOptions {
          center(Point.fromLngLat(24.753574, 59.436962))
          zoom(8.0)
        })
      }

      DashboardScreen(
        mapBoxStyle = style.value.style,
        camera = camera.value,
        onNavigationClick = { route ->
          navController.navigate(route) {
            popUpTo(NavRoute.Dashboard)
          }
        },
        onChangeStyle = {
          style.value = MapViewStyle.values()[(style.value.ordinal + 1) % MapViewStyle.values().size]
        }
      )
    } else {
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
          locationPermissionsState.launchMultiplePermissionRequest()
        }) {
          Text("Permissions")
        }
      }

    }
  }
}

@Composable
private fun DashboardScreen(
  mapBoxStyle: String,
  camera: CameraOptions,
  onNavigationClick: (NavRoute) -> Unit,
  onChangeStyle: () -> Unit
) {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.TopEnd
  ) {
    MapboxComponent(
      mapBoxStyle = mapBoxStyle,
      cameraOptions = camera
    )

    Column {
      IconButton(
        modifier = Modifier.padding(bottom = 16.dp, top = 40.dp),
        onClick = { onNavigationClick.invoke(NavRoute.Help) }
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_about),
          contentDescription = null
        )
      }
      IconButton(
        modifier = Modifier.padding(bottom = 16.dp),
        onClick = { onChangeStyle.invoke() }
      ) {
        Icon(
          painter = painterResource(id = R.drawable.ic_layers),
          contentDescription = null
        )
      }
    }
  }
}

@Composable
private fun MapboxComponent(
  mapBoxStyle: String,
  cameraOptions: CameraOptions
) {
  AndroidView(
    modifier = Modifier.fillMaxSize(),
    factory = {
      MapView(it).apply {

        //location.setLocationProvider()
      }
    },
    update = {
      it.logo.updateSettings { enabled = false }
      it.attribution.updateSettings { enabled = false }
      it.getMapboxMap().apply {
        loadStyleUri(mapBoxStyle)

        flyTo(cameraOptions)
      }
    }
  )
}

private enum class MapViewStyle(val style: String) {
  STREET(Style.MAPBOX_STREETS),
  SATELLITE(Style.SATELLITE),
}
