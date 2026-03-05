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
import com.example.spaceapi.composables.MainScreen
import com.example.spaceapi.composables.SecondPage
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
            startDestination = Routes.Home.route
        ) {

            composable(Routes.Home.route) {
                MainScreen(viewModel = viewModel, navController = navHostController)
            }

            composable(Routes.Detail.route,
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