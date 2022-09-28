package com.example.navermaptest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.navermaptest.model.Station
import com.example.navermaptest.network.ApiClient
import com.example.navermaptest.network.Repository
import com.example.navermaptest.ui.theme.NaverMapTestTheme
import com.google.android.gms.common.api.GoogleApiClient
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.overlay.OverlayImage
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private var stationList: MutableList<Station> = mutableListOf()

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel
    private val apiClient = ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewModel 초기화
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(Repository(apiClient))
        )[MainViewModel::class.java]

        setContent {
            observeViewModel()
            MainView()
        }
    }

    fun observeViewModel() {
        viewModel.getStation(1, 100)
        viewModel.isSuccess.observe(this@MainActivity) {
            for (station in it) {
                stationList.add(station)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainView()
}


@Composable
fun MainView() {
    NaverMapTestTheme() {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalDrawer(
            drawerState = drawerState,
            gesturesEnabled = false,
            drawerShape = MaterialTheme.shapes.large,
            drawerContent = {
                drawerMenuArea(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                    scope, drawerState
                )
            },
            content = {
                Box() {
                    MapArea()
                }
            }
        )
    }
}

@Composable
fun drawerMenuArea(
    modifier: Modifier,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    Button(
        modifier = modifier,
        onClick = { scope.launch { drawerState.close() } },
        content = { Text("Close Drawer") }
    )
}


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapArea() {
    NaverMap(
        modifier = Modifier.fillMaxSize()
    ) {
        stationList.forEach {
            Marker(
                state = MarkerState(position = LatLng(it.lat.toDouble(), it.longi.toDouble())),
                icon = OverlayImage.fromResource(R.drawable.ic_launcher_foreground)
            )
        }
    }
}
