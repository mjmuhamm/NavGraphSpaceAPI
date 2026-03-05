package com.example.spaceapi.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.spaceapi.viewModel.SecondState
import com.example.spaceapi.viewModel.SecondViewModel

@Composable
fun SecondPage(id: String, viewModel: SecondViewModel, navController: NavController) {

    LaunchedEffect(id) {
        viewModel.secondPage(id)
    }

    val state by viewModel.secondState.observeAsState(SecondState.Loading)
    when (state) {
        is SecondState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

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
                                .clickable(onClick = { navController.popBackStack() })

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
