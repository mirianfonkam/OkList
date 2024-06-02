package com.mdevor.oklist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.mdevor.oklist.presentation.navigation.StartNavigationViewModel
import com.mdevor.oklist.presentation.navigation.TaskNavGraph
import com.mdevor.oklist.presentation.theme.OkListTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            OkListTheme {
                val navController = rememberNavController()
                val viewModel: StartNavigationViewModel = hiltViewModel()
                val viewState = viewModel.uiState.collectAsStateWithLifecycle().value

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskNavGraph(
                        navController = navController,
                        navigationViewState = viewState
                    )
                }
            }
        }
    }
}