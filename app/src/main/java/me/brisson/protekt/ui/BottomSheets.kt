package me.brisson.protekt.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import me.brisson.protekt.R
import me.brisson.protekt.domain.model.Item
import me.brisson.protekt.ui.theme.LightGray
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.ui.theme.montserrat

@ExperimentalComposeUiApi
@Composable
fun BottomSheet(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    val transitionState = remember {
        MutableTransitionState(false).apply { targetState = true }
    }
    val interactionSource = remember { MutableInteractionSource() }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {

        AnimatedVisibility(
            visibleState = transitionState,
            enter = slideInVertically(
                initialOffsetY = { it }
            ),
            exit = slideOutVertically(
                targetOffsetY = { it }
            ),
            content = {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Transparent)
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = onDismissRequest
                            )
                    )
                    content()
                }
            }
        )
    }
}

@Composable
fun CreateItemBottomSheet(
    modifier: Modifier = Modifier,
    onType: (Item.Type) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(bottom = 30.dp)
            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp, bottom = 20.dp),
            text = stringResource(id = R.string.create_item_bottom_sheet_title),
            style = TextStyle(
                fontFamily = montserrat,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            textAlign = TextAlign.Center
        )

        enumValues<Item.Type>().toList().forEach { type ->
            CreateItemBottomSheetItem(
                modifier = Modifier.padding(
                    vertical = 4.dp,
                    horizontal = 20.dp
                ),
                type = type,
                onClick = { onType(type) }
            )
        }

    }
}

@Composable
fun CreateItemBottomSheetItem(
    modifier: Modifier = Modifier,
    type: Item.Type,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(LightGray)
                .padding(5.dp)
        ) {
            Icon(
                painter = painterResource(id = type.drawableResId),
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = type.stringResId),
            style = TextStyle(fontFamily = montserrat)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateItemBottomSheetItemPreview() {
    ProteKTTheme {
        CreateItemBottomSheet(modifier = Modifier, onType = { })
    }
}