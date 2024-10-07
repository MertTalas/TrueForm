package com.mert.trueform.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.mert.trueform.domain.model.Exercise
import com.mert.trueform.presentation.navigation.Screen
import com.mert.trueform.presentation.viewmodel.ExerciseListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListScreen(
    navController: NavController,
    viewModel: ExerciseListViewModel = hiltViewModel()
) {
    val exercises by viewModel.exercises.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    val listState = rememberLazyListState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == exercises.lastIndex && !isSearching) {
                    viewModel.loadExercises()
                }
            }
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                CenterAlignedTopAppBar(
                    title = { Text("Exercises") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    SearchBar(
                        searchQuery = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onSearch = {
                            if (searchQuery.text.isNotEmpty()) {
                                viewModel.getSearchedExercises(searchQuery.text)
                            } else {
                                viewModel.clearSearch()
                            }
                        },
                        onClear = {
                            searchQuery = TextFieldValue("")
                            viewModel.clearSearch()
                        }
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        ExerciseList(
            exercises = exercises,
            listState = listState,
            paddingValues = paddingValues,
            isLoading = isLoading,
            isSearching = isSearching,
            onItemClick = { exerciseId ->
                navController.navigate(Screen.ExerciseDetail.createRoute(exerciseId))
            }
        )
    }
}

@Composable
fun SearchBar(
    searchQuery: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            label = { Text("Search Exercises") },
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        keyboardController?.hide()
                    }
                },
            trailingIcon = {
                if (searchQuery.text.isNotEmpty()) {
                    IconButton(onClick = onClear) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear Search")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = onSearch) {
            Text("Search")
        }
    }
}

@Composable
fun ExerciseList(
    exercises: List<Exercise>,
    listState: LazyListState,
    paddingValues: PaddingValues,
    isLoading: Boolean,
    isSearching: Boolean,
    onItemClick: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        state = listState,
        contentPadding = paddingValues,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                )
            }
    ) {
        itemsIndexed(exercises) { _, exercise ->
            ExerciseItem(
                exercise = exercise,
                onItemClick = { onItemClick(exercise.id) }
            )
        }

        if (isLoading && !isSearching) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = exercise.gifUrl),
            contentDescription = exercise.name,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = exercise.name, color = MaterialTheme.colorScheme.primary)
            Text(text = "Target: ${exercise.target}", color = MaterialTheme.colorScheme.onSurface)
        }
    }
}