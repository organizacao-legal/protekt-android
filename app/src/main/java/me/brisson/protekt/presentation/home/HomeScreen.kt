package me.brisson.protekt.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.brisson.protekt.R
import me.brisson.protekt.domain.model.Credential
import me.brisson.protekt.domain.model.mockedItemList
import me.brisson.protekt.ui.AppButton
import me.brisson.protekt.ui.theme.DarkGray
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.ui.theme.montserrat
import me.brisson.protekt.utils.ItemTypes

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold(
        floatingActionButton = {
            AppButton(modifier = Modifier.padding(4.dp), onClick = { }) {
                Icon(
                    modifier = Modifier.padding(horizontal = 0.dp),
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerScaffoldPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerScaffoldPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                HomeHeader(
                    modifier = Modifier.padding(bottom = 12.dp),
                    onSearchInputChange = { },
                    onSearch = { },
                    onMenu = { },
                    chips = enumValues<ItemTypes>().toList(),
                    selectedChips = { }
                )
            }

            item {
                Text(
                    modifier = Modifier.padding(start = 24.dp, top = 12.dp, bottom = 8.dp),
                    text = stringResource(id = R.string.my_vault),
                    style = TextStyle(
                        fontFamily = montserrat,
                        color = DarkGray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            items(mockedItemList) { item ->
                when (item) {
                    is Credential -> {
                        CredentialItem(
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                            credential = item,
                            onClick = { }
                        )
                    }
                }
            }
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