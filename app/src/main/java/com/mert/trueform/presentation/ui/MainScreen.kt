package com.mert.trueform.presentation.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.mert.trueform.presentation.navigation.NavigationGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    MaterialTheme {
        Surface {
            NavigationGraph(navController = navController)
        }
    }
}