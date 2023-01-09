package me.brisson.protekt.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.brisson.protekt.R
import me.brisson.protekt.ui.theme.DarkGray
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.ui.theme.montserrat


@ExperimentalMaterial3Api
@Composable
fun CustomChip(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    text: String,
    onClick: () -> Unit
) {
    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = text,
                style = TextStyle(
                    fontFamily = montserrat,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
            disabledLabelColor = DarkGray,
            disabledContainerColor = DarkGray
        ),
        leadingIcon = {
            if (selected) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = stringResource(id = R.string.icon_check_content_description))
            }
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun ChipGroup(
    chips: List<String>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    selectedChips: (items: List<String>) -> Unit
) {
    val pairedChips = mutableListOf<Pair<String, Boolean>>().apply {
        addAll(chips.map { Pair(it, false) })
    }

    LazyRow(modifier = modifier, contentPadding = contentPadding) {
        itemsIndexed(pairedChips) { index, item ->
            var selected by remember { mutableStateOf(item.second) }

            val itemModifier = when (index) {
                0 -> { Modifier.padding(end = 4.dp) }
                chips.lastIndex -> { Modifier.padding(start = 4.dp) }
                else -> { Modifier.padding(horizontal = 4.dp) }
            }

            CustomChip(
                modifier = itemModifier,
                text = item.first,
                selected = selected,
                onClick = {
                    selected = !selected
                    pairedChips[index] = Pair(item.first, selected)
                    selectedChips(pairedChips.filter { it.second }.map { it.first })
                }
            )
        }
    }
}


@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun PreviewCustomChip() {
    ProteKTTheme {
        val chips = listOf("Credential", "Credit card", "Identities", "Secret note")
        ChipGroup(modifier = Modifier.fillMaxWidth(), chips = chips, selectedChips = { })
    }
}