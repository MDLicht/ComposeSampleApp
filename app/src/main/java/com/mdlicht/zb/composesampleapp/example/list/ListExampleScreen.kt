package com.mdlicht.zb.composesampleapp.example.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mdlicht.zb.composesampleapp.R

@Composable
fun ListExampleScreen(
    viewModel: ListExampleViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentUiState = uiState

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            ListExampleTextField(viewModel)

            when (currentUiState) {
                is ListUiState.Idle -> {
                    ListExampleIdle()
                }

                is ListUiState.Loading -> {
                    ListExampleLoading()
                }

                is ListUiState.Empty -> {
                    ListExampleEmpty()
                }

                is ListUiState.Result -> {
                    ListExampleResult(currentUiState)
                }
            }
        }
    }
}

@Composable
fun ListExampleTextField(
    viewModel: ListExampleViewModel
) {
    var input by remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = input,
        onValueChange = { value ->
            input = value
            viewModel.searchAnimal(animalName = value)
        },
        placeholder = {
            Text(text = stringResource(id = R.string.sample_using_list_input_hint))
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = input.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                IconButton(onClick = {
                    input = ""
                    viewModel.clearInput()
                }) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_delete),
                        contentDescription = "Clear input"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        singleLine = true,
        maxLines = 1,
    )
}

@Composable
fun ListExampleIdle() {
    Box(modifier = Modifier.fillMaxSize())
}

@Composable
fun ListExampleLoading() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = 10) {
            ListExampleLoadingShimmer()
        }
    }
}

@Composable
fun ListExampleLoadingShimmer() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 15.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .background(color = Color.LightGray),
        )
        HorizontalDivider()
    }
}

@Composable
fun ListExampleEmpty() {
    Box(modifier = Modifier.padding(horizontal = 25.dp, vertical = 15.dp)) {
        Text(text = stringResource(id = R.string.sample_using_list_empty_result))
    }
}

@Composable
fun ListExampleResult(uiState: ListUiState.Result) {
    val keyword = uiState.keyword
    val list = uiState.list
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = list.size) {
            ListExampleResultItem(keyword = keyword, itemText = list[it])
        }
    }
}

@Composable
fun ListExampleResultItem(keyword: String, itemText: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 15.dp),
                text = buildAnnotatedString {
                    val startIndex = itemText.lowercase().indexOf(keyword.lowercase())
                    val highlightStartIndex = startIndex.coerceAtLeast(0)
                    val highlightEndIndex = if (startIndex < 0) {
                        0
                    } else {
                        highlightStartIndex + keyword.length
                    }
                    append(itemText.substring(0, highlightStartIndex))
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append(itemText.substring(highlightStartIndex, highlightEndIndex))
                    }
                    append(itemText.substring(highlightEndIndex))
                },
                fontSize = 14.sp,
            )
            HorizontalDivider()
        }
    }
}