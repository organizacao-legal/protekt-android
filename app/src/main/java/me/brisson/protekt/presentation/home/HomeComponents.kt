package me.brisson.protekt.presentation.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
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
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.brisson.protekt.R
import me.brisson.protekt.ui.ChipGroup
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.ui.theme.montserrat
import me.brisson.protekt.utils.ItemTypes

@ExperimentalMaterial3Api
@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    chips: List<ItemTypes>,
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
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
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
                                contentDescription = null,
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
                                contentDescription = null
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
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    )
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
                chips = enumValues<ItemTypes>().toList(),
                selectedChips = { }
            )
        }
    }
}