package me.brisson.protekt.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.ui.theme.montserrat

@Composable
fun HomeScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "HOME",
            style = TextStyle(
                fontFamily = montserrat,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    ProteKTTheme {
        HomeScreen()
    }
}