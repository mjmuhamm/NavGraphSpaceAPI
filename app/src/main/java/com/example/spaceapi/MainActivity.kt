package com.example.spaceapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
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
                    MainScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: SpaceViewModel, modifier: Modifier = Modifier) {

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
                        SpaceItems(info)
                    }
                }
            }
            is SpaceState.Error -> {}
        }

    }
}

@Composable
fun SpaceItems(info: Result) {
    Card(modifier = Modifier.fillMaxWidth().clickable {


    }) {
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