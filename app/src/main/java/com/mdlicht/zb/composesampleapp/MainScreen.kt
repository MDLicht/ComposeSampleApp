package com.mdlicht.zb.composesampleapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(
    navController: NavController,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        item(key = "UsingList") {
            SampleListItem(
                itemText = stringResource(id = R.string.sample_using_list),
                onClick = {
                    navController.navigate("listExampleScreen")
                },
            )
        }
    }
}

@Composable
fun SampleListItem(
    itemText: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp, vertical = 15.dp),
                textAlign = TextAlign.Start,
                text = itemText,
            )
            HorizontalDivider()
        }
    }
}