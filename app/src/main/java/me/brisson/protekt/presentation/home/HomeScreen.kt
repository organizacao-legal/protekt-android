package me.brisson.protekt.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.brisson.protekt.R
import me.brisson.protekt.domain.model.Credential
import me.brisson.protekt.domain.model.Item
import me.brisson.protekt.ui.components.AppButton
import me.brisson.protekt.ui.components.BottomSheet
import me.brisson.protekt.ui.components.CreateItemBottomSheet
import me.brisson.protekt.ui.theme.DarkGray
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.ui.theme.montserrat

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onItem: (itemId: String) -> Unit,
    onCreate: (itemType: Item.Type) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var openDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            AppButton(
                modifier = Modifier.padding(4.dp),
                onClick = { openDialog = true }
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = 0.dp),
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(id = R.string.icon_add_content_description),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerScaffoldPadding ->

        if (openDialog) {
            BottomSheet(
                onDismissRequest = { openDialog = false },
            ) {
                CreateItemBottomSheet(
                    onType = { type ->
                        onCreate(type)
                        openDialog = false
                    }
                )
            }
        }

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
                    chips = enumValues<Item.Type>().toList(),
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

            uiState.itemList.let { items ->
                if (items.isNullOrEmpty()) {
                    item {
                        ItemListEmptyState(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 50.dp),
                            onCreateItem = { }
                        )
                    }
                } else {
                    items(items) { item ->
                        when (item) {
                            is Credential -> {
                                CredentialItem(
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 2.dp
                                    ),
                                    credential = item,
                                    onClick = { onItem(item.id) }
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewHomeScreen() {
    ProteKTTheme {
        HomeScreen(onItem = { }, onCreate = { })
    }
}