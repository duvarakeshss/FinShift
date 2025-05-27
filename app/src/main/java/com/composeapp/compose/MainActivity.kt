package com.composeapp.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeapp.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Yellow) // Background color for NavigationBar
                        ) {
                            // Add your NavigationBarItems here
                            NavigationBarItem(
                                selected = false, // Set 'selected' based on current screen
                                onClick = { /* Handle item click */ },
                                icon = { /* Your icon composable */ },
                                label = {
                                    Text(
                                        text = "Home",
                                        color = Color.Black
                                    )
                                }
                            )
                            // Add more NavigationBarItems as needed...
                        }
                    }
                ) { innerPadding ->
                    // Content of your screen, use innerPadding to avoid overlap with navbar
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "dk",
                                    color = Color.Black,
                                    fontSize = 20.sp
                                )
                            },
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = Color.Yellow
                            )
                        )
                        Greeting("Dk") // Updated call for Greeting composable
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun TopAppBarDefaults.smallTopAppBarColors(containerColor: Color): TopAppBarColors {
        TODO("Not yet implemented")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column( // Column to center content vertically
        modifier = modifier
            .fillMaxSize() // Fill the available space on the screen
            .padding(16.dp), // Add some padding around the text
        verticalArrangement = Arrangement.Center, // Vertically center the content
        horizontalAlignment = Alignment.CenterHorizontally // Horizontally center the content
    ) {
        Text(
            text = "Hello $name!",
            color = Color.Red,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTheme {
        Greeting("dk")
    }
}
