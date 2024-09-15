package com.mert.trueform.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mert.trueform.presentation.ui.ExerciseListScreen
import com.mert.trueform.presentation.ui.ExerciseDetailScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.ExerciseList.route) {
        composable(route = Screen.ExerciseList.route) {
            ExerciseListScreen(navController = navController)
        }
        composable(
            route = Screen.ExerciseDetail.route,
            arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId") ?: ""
            ExerciseDetailScreen(navController = navController, exerciseId = exerciseId)
        }
    }
}