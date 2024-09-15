package com.mert.trueform.presentation.navigation

sealed class Screen(val route: String) {

    data object ExerciseList : Screen("exercise_list")
    data object ExerciseDetail : Screen("exercise_detail/{exerciseId}") {
        fun createRoute(exerciseId: String) = "exercise_detail/$exerciseId"
    }
}