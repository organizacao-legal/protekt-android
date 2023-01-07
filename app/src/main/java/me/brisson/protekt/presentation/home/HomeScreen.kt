package me.brisson.protekt.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.utils.ItemTypes

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            HomeHeader(
                onSearchInputChange = { },
                onSearch = { },
                onMenu = { },
                chips = enumValues<ItemTypes>().toList(),
                selectedChips = { }
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewHomeScreen() {
    ProteKTTheme {
        HomeScreen()
    }
}