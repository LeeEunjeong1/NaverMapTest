package com.example.navermaptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.navermaptest.ui.theme.NaverMapTestTheme
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()
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
                    SearchBarArea(scope, drawerState)
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

@Composable
fun SearchBarArea(
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
) {
    Card(
        elevation = 16.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        Row() {
            IconButton(
                onClick = { coroutineScope.launch { drawerState.open() } }
            ) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "slide menu"
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                text = "text",
                color = Color.Gray)
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapArea() {
    NaverMap(
        modifier = Modifier.fillMaxSize()
    )
}