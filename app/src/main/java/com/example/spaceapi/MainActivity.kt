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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.spaceapi.model.firstPage.Result
import com.example.spaceapi.routes.Routes
import com.example.spaceapi.ui.theme.SpaceAPITheme
import com.example.spaceapi.viewModel.SecondState
import com.example.spaceapi.viewModel.SecondViewModel
import com.example.spaceapi.viewModel.SpaceState
import com.example.spaceapi.viewModel.SpaceViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SpaceViewModel by viewModels()
    private val viewModel2: SecondViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpaceAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun MyApp(modifier: Modifier) {
        val navHostController = rememberNavController()
        AppNavHost(navHostController = navHostController)


    }

    @Composable
    fun AppNavHost(navHostController: NavHostController) {
        NavHost(
            navController = navHostController,
            startDestination = Routes.HOME
        ) {

            composable(Routes.HOME) {
                MainScreen(viewModel = viewModel, navController = navHostController)
            }

            composable(Routes.DETAIL,
                listOf(
                    navArgument("userId") {
                        type = NavType.StringType
                    }
                )) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId") ?: "Unknown"
                SecondPage(id = userId, viewModel = viewModel2, { navHostController.popBackStack() })

            }
        }
    }


//    @Composable
//    fun Home(
//        viewModel: SpaceViewModel,
//        viewModel2: SecondViewModel,
//        modifier: Modifier = Modifier,
//        onNavigateToDetail : () -> Unit
//    ) {
//        var currentScreen by rememberSaveable() { mutableStateOf(true) }
//        var passingId by rememberSaveable { mutableStateOf("") }
//
//        if (currentScreen) {
//            MainScreen(viewModel = viewModel, onNavigateToDetail = { id ->
//                passingId = id
//                currentScreen = false
//            })
//        } else {
//            SecondPage(id = passingId, viewModel = viewModel2, onBack = {})
//        }
//    }

    @Composable
    fun MainScreen(viewModel: SpaceViewModel, navController: NavController) {

        val state by viewModel.spaceState.observeAsState(SpaceState.Loading)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is SpaceState.Loading -> CircularProgressIndicator()
                is SpaceState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items((state as SpaceState.Success).data) { info ->
                            SpaceItems(info, onNavigate = { id ->
                                navController.navigate("detail/${id}")
                            })
//                        SpaceItems(info, onNavigate = { onNavigate(info.id.toString()) } )
                        }
                    }
                }

                is SpaceState.Error -> {}
            }

        }
    }

    @Composable
    fun SpaceItems(info: Result, onNavigate: (String) -> Unit = {}) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onNavigate(info.id.toString()) })
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = info.image_url,
                    contentDescription = info.summary,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )

                Text(text = info.title, modifier = Modifier.padding(start = 20.dp))
            }
        }
    }


    @Composable
    fun SecondPage(id: String, viewModel: SecondViewModel, onBack: () -> Unit) {

        LaunchedEffect(id) {
            viewModel.secondPage(id)
        }

        val state by viewModel.secondState.observeAsState(SecondState.Loading)
        when (state) {
            is SecondState.Loading -> CircularProgressIndicator()

            is SecondState.Success -> {
                val info = (state as SecondState.Success).data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 25.dp, top = 10.dp)
                    ) {
                        Box(modifier = Modifier) {

                            Text(
                                "<",
                                color = Color.Gray,
                                fontSize = 50.sp,
                                fontWeight = FontWeight.Light,
                                modifier = Modifier
                                    .padding(0.dp, 0.dp, 20.dp, 0.dp)
                                    .clickable(onClick = onBack)

                            )

                            Text(
                                "Second Page",
                                color = Color.Black,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,


                                modifier = Modifier.padding(100.dp, 13.dp, 0.dp, 0.dp)
                            )
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(top = 10.dp),
                        thickness = 0.8.dp,
                        color = Color.Gray
                    )
                    Row(
                        modifier = Modifier
                            .padding(30.dp)
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = info.image_url,
                            contentDescription = info.summary,
                            modifier = Modifier.size(150.dp)
                        )
                        Column {
                            Text(
                                info.title,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 10.dp, top = 20.dp)
                            )
                            Text(
                                "Author: ${info.authors[0].name}",
                                modifier = Modifier.padding(start = 10.dp, top = 8.dp)
                            )
                        }
                    }
                    Column(modifier = Modifier.padding(start = 25.dp, end = 15.dp)) {
                        Text("News Site", modifier = Modifier.padding(bottom = 7.dp))
                        Text(
                            info.news_site,
                            modifier = Modifier,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            "News Article",
                            modifier = Modifier.padding(bottom = 7.dp, top = 20.dp)
                        )
                        Text(info.url)

                        Text(
                            "Summary",
                            modifier = Modifier.padding(bottom = 7.dp, top = 20.dp)
                        )
                        Text(info.summary)
                    }

                }
            }

            is SecondState.Error -> {
                Column() {
                    Text("Id passed: $id", modifier = Modifier.padding(30.dp))
                    Text(
                        "Error loading second page: ${(state as SecondState.Error).message}",
                        modifier = Modifier.padding(30.dp)
                    )
                }
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

}