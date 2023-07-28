package com.example.omadachallengechenige

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.AppTheme
import com.example.omadachallengechenige.flickrimages.FullScreenPhotoScreen
import com.example.omadachallengechenige.flickrimages.ImagesGrid
import com.example.omadachallengechenige.flickrimages.ImagesViewModel
import com.example.omadachallengechenige.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val imagesViewModel: ImagesViewModel by viewModels()

    private val pushNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        //viewModel.inputs.onTurnOnNotificationsClicked(granted)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pushNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {

            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //ImagesGrid(state = imagesViewModel.imagesState, imagesViewModel::onSearchEvent)


                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MainScreen.route
                    ) {
                        composable(route = Screen.MainScreen.route) {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                ImagesGrid(
                                    navController,
                                    state = imagesViewModel.imagesState,
                                    imagesViewModel::onSearchEvent
                                )
                            }
                        }

                        composable(
                            route = Screen.DetailScreen.route + "/{photoUrl}",
                            arguments = listOf(
                                navArgument("photoUrl") {
                                    type = NavType.StringType
                                    nullable = true
                                })
                        ) { entry ->
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                FullScreenPhotoScreen(
                                    navController,
                                    state = imagesViewModel.imagesState
                                )
                            }
                        }
                    }
                }
            }
//            AppTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    ImagesGrid(state = imagesViewModel.imagesState, imagesViewModel::onSearchEvent)
//                }
//            }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}