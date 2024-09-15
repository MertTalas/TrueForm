package com.mert.trueform.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.mert.trueform.domain.model.Exercise
import com.mert.trueform.presentation.viewmodel.ExerciseDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(
    navController: NavController,
    exerciseId: String,
    viewModel: ExerciseDetailViewModel = hiltViewModel()
) {
    val exerciseDetail = viewModel.exerciseDetail.collectAsState()

    LaunchedEffect(exerciseId) {
        viewModel.loadExerciseDetail(exerciseId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Exercise Detail") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        exerciseDetail.let { detail ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(detail.value?.gifUrl)
                            .decoderFactory(if (android.os.Build.VERSION.SDK_INT >= 28) ImageDecoderDecoder.Factory() else GifDecoder.Factory())
                            .size(Size.ORIGINAL)
                            .crossfade(true)
                            .build(),
                        contentDescription = detail.value?.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .background(Color.White)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Name: ${detail.value?.name}", style = MaterialTheme.typography.headlineSmall)
                    Text("Body Part: ${detail.value?.bodyPart}", style = MaterialTheme.typography.bodyLarge)
                    Text("Equipment: ${detail.value?.equipment}", style = MaterialTheme.typography.bodyLarge)
                    Text("Target: ${detail.value?.target}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Secondary Muscles:", style = MaterialTheme.typography.headlineSmall)
                }

                detail.value?.secondaryMuscles?.let { muscles ->
                    items(muscles.size) { index ->
                        Text(
                            text = muscles[index],
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Instructions:", style = MaterialTheme.typography.headlineSmall)
                }

                detail.value?.instructions?.let { instructions ->
                    items(instructions.size) { index ->
                        Text(
                            text = "${index+1}. ${instructions[index]}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
