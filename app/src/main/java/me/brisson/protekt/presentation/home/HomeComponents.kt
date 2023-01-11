package me.brisson.protekt.presentation.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.brisson.protekt.R
import me.brisson.protekt.domain.model.Credential
import me.brisson.protekt.domain.model.Item
import me.brisson.protekt.ui.ChipGroup
import me.brisson.protekt.ui.theme.DarkGray
import me.brisson.protekt.ui.theme.MidGray
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.ui.theme.montserrat

@ExperimentalMaterial3Api
@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    chips: List<Item.Type>,
    selectedChips: (items: List<String>) -> Unit,
    onSearchInputChange: ((value: String) -> Unit)? = null,
    onSearch: (value: String) -> Unit,
    onMenu: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, end = 24.dp, start = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onMenu) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.icon_menu_content_description)
                )
            }
            SearchInput(onSearch = onSearch, onValueChange = onSearchInputChange)
        }
        ChipGroup(
            modifier = Modifier.fillMaxWidth(),
            chips = chips.map { stringResource(id = it.stringResId) },
            contentPadding = PaddingValues(horizontal = 24.dp),
            selectedChips = selectedChips
        )
    }
}

@Composable
fun SearchInput(
    modifier: Modifier = Modifier,
    onValueChange: ((value: String) -> Unit)? = null,
    onSearch: (value: String) -> Unit,
) {
    var input by remember { mutableStateOf(TextFieldValue("")) }
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        modifier = modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .height(40.dp)
            .background(color = MaterialTheme.colorScheme.background)
            .onFocusChanged { isFocused = it.isFocused },
        value = input,
        onValueChange = { inputValue ->
            input = inputValue
            onValueChange?.let {
                it(inputValue.text)
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            focusManager.clearFocus()
            input = TextFieldValue("")
            onSearch(input.text)
        }),
        textStyle = TextStyle(fontFamily = montserrat),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = { innerTextField ->
            Crossfade(targetState = isFocused) { scope ->
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (scope) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 20.dp)
                        ) {
                            innerTextField()
                        }
                        IconButton(
                            onClick = {
                                focusManager.clearFocus()
                                input = TextFieldValue("")
                            }
                        ) {
                            Icon(
                                modifier = Modifier,
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = stringResource(id = R.string.icon_clear_content_description),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier.padding(start = 15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.kotlin_logo),
                                contentDescription = stringResource(id = R.string.app_image_logo_content_description)
                            )
                            Text(
                                text = stringResource(id = R.string.app_name).toUpperCase(Locale.current),
                                style = TextStyle(
                                    fontFamily = montserrat,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    letterSpacing = 2.sp
                                )
                            )
                        }
                        Icon(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            imageVector = Icons.Rounded.Search,
                            contentDescription = stringResource(id = R.string.icon_search_content_description),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun CredentialItem(
    modifier: Modifier = Modifier,
    credential: Credential,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() }
            .padding(8.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .padding(start = 0.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .fillMaxHeight()
                    .aspectRatio(1f),
                model = credential.image,
                placeholder = painterResource(id = R.drawable.image_placeholder),
                contentDescription = stringResource(
                    id = R.string.credential_image_content_description,
                    credential.name
                ),
                error = painterResource(id = R.drawable.image_placeholder)
            )

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = credential.name,
                    style = TextStyle(
                        fontFamily = montserrat,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = credential.username,
                    style = TextStyle(
                        fontFamily = montserrat,
                        fontWeight = FontWeight.Medium,
                        color = DarkGray
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        IconButton(modifier = Modifier.size(30.dp), onClick = onClick) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Rounded.ArrowForwardIos,
                contentDescription = stringResource(id = R.string.icon_arrow_forward_content_description)
            )
        }

    }
}

@Composable
fun ItemListEmptyState(
    modifier: Modifier = Modifier,
    onCreateItem: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.sleeping_cloud),
            contentDescription = stringResource(id = R.string.item_list_empty_state_image_content_description)
        )
        Text(
            modifier = Modifier.offset(y = (-10).dp),
            text = stringResource(id = R.string.item_list_empty_state_message),
            style = TextStyle(
                fontFamily = montserrat,
                fontSize = 16.sp,
                color = MidGray,
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            modifier = Modifier
                .offset(y = (-10).dp)
                .padding(top = 3.dp)
                .clip(RoundedCornerShape(4.dp))
                .clickable { onCreateItem() }
                .padding(horizontal = 6.dp, vertical = 3.dp),
            text = stringResource(id = R.string.item_list_empty_state_action),
            style = TextStyle(
                fontFamily = montserrat,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}


@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun PreviewSearchInput() {
    ProteKTTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            HomeHeader(
                onSearchInputChange = { },
                onSearch = { },
                onMenu = { },
                chips = enumValues<Item.Type>().toList(),
                selectedChips = { }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCredentialItem() {
    ProteKTTheme {
        CredentialItem(
            modifier = Modifier.padding(horizontal = 24.dp),
            credential = Credential(
                image = "https://logo.clearbit.com/https://twitter.com",
                name = "Twitter",
                username = "@JonDoe",
                url = "http://twitter.com"
            ),
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemsEmptyState() {
    ProteKTTheme {
        ItemListEmptyState(modifier = Modifier.fillMaxWidth(), onCreateItem = { })
    }
}