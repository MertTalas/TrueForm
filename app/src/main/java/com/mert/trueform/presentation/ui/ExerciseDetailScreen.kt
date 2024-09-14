package com.mert.trueform.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.mert.trueform.domain.model.Exercise

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(navController: NavController, exerciseId: String?) {

    val exercise = Exercise(
        id = exerciseId ?: "",
        name = "Push-Up",
        bodyPart = "Chest",
        equipment = "None",
        gifUrl = "https://example.com/pushup.gif",
        target = "Pectorals"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(exercise.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = exercise.gifUrl),
                contentDescription = exercise.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Body Part: ${exercise.bodyPart}",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(text = "Target: ${exercise.target}", style = MaterialTheme.typography.bodySmall)
            Text(
                text = "Equipment: ${exercise.equipment}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
