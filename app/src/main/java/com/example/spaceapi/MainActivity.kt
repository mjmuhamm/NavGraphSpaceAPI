package com.example.spaceapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.spaceapi.model.firstPage.Result
import com.example.spaceapi.ui.theme.SpaceAPITheme
import com.example.spaceapi.viewModel.SpaceState
import com.example.spaceapi.viewModel.SpaceViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SpaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpaceAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Home(viewModel,modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Home(viewModel: SpaceViewModel, modifier: Modifier = Modifier) {
    var currentScreen by rememberSaveable() { mutableStateOf(true) }

    if (currentScreen) {
        MainScreen(viewModel = viewModel, onNavigate = { currentScreen = false})
    } else {
//        SecondPage(onNavigate = { currentScreen = true })
    }
}

@Composable
fun MainScreen(viewModel: SpaceViewModel, onNavigate: () -> Unit = {}) {

    LaunchedEffect(Unit) {
        viewModel.getInfo()
    }

    val state by viewModel.spaceState.observeAsState(SpaceState.Loading)
    Box(modifier = Modifier.fillMaxSize().padding(top = 16.dp), contentAlignment = Alignment.Center) {
        when(state) {
            is SpaceState.Loading -> CircularProgressIndicator()
            is SpaceState.Success -> {
                LazyColumn(contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items((state as SpaceState.Success).data) { info ->
                        SpaceItems(info, onNavigate = onNavigate)
                    }
                }
            }
            is SpaceState.Error -> {}
        }

    }
}

@Composable
fun SpaceItems(info: Result, onNavigate: () -> Unit = {}) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onNavigate)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {

            AsyncImage(
                model = info.image_url,
                contentDescription = info.summary,
                modifier = Modifier.size(100.dp).clip(CircleShape)
            )

            Text(text = info.title, modifier = Modifier.padding(start = 20.dp))
        }
    }
}


@Composable
@Preview
fun SecondPage() {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Row(modifier = Modifier.fillMaxWidth().padding(25.dp)) {
            Box(modifier = Modifier) {
                Text(
                    "<",
                    color = Color.Gray,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 20.dp, 0.dp)

                )

            }

            // Conditionally show the new composable

//                    Text(
//                        "<",
//                        color = Color.Green,
//                        fontSize = 50.sp,
//                        fontFamily = fonts,
//                        fontWeight = FontWeight.Light,
//                        modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp).clickable {
//
//                        }
//                    )

            Text(
                "Second Page",
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Normal,
                
                modifier = Modifier.padding(10.dp, 14.dp, 0.dp, 0.dp)
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 15.dp),
            thickness = 0.8.dp,
            color = Color.Gray
        )
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun GreetingPreview() {
    SpaceAPITheme {
        Greeting("Android")
    }
}