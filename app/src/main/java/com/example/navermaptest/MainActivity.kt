package com.example.navermaptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import com.example.navermaptest.network.ApiClient
import com.example.navermaptest.network.Repository
import com.example.navermaptest.ui.theme.NaverMapTestTheme
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private lateinit var viewModel: MainViewModel
class MainActivity : ComponentActivity() {

    private val apiClient = ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewModel 초기화
        viewModel = ViewModelProvider(this, ViewModelFactory(Repository(apiClient)))[MainViewModel::class.java]
        viewModel.getStation(1, 100)
        setContent {
            MainView()
        }
    }
}

@Preview(showBackground = true)
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
        val imageUrl = "http://dh.aks.ac.kr/Edu/wiki/images/4/4c/%ED%96%84.jpg"

        viewModel.isSuccess.observeAsState().value?.forEach {
            Marker(
                state = MarkerState(position = LatLng(it.lat.toDouble(), it.longi.toDouble())),
                icon = OverlayImage.fromResource(R.drawable.ic_station),
                width = 20.dp,
                height = 20.dp
            )
        }
    }
}
