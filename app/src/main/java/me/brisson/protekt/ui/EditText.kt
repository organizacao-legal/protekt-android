package me.brisson.protekt.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.brisson.protekt.R
import me.brisson.protekt.ui.theme.*

@Composable
fun EditText(
    modifier: Modifier = Modifier,
    text: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    enabled: Boolean = true,
    error: Boolean = false,
    correct: Boolean = false,
    label: (@Composable RowScope.() -> Unit)? = null,
    trailingComponent: (@Composable RowScope.() -> Unit)? = null,
    onValueChange: ((input: String) -> Unit)? = null,
) {

    var input by remember { mutableStateOf(TextFieldValue(text ?: "")) }
    var isFocused by remember { mutableStateOf(false) }
    var state by remember {
        mutableStateOf(
            if (error) {
                EditTextState.Error
            } else if (correct) {
                EditTextState.Correct
            } else {
                EditTextState.Default
            }
        )
    }
    val componentHeight = 50.dp

    BasicTextField(
        modifier = modifier
            .height(componentHeight)
            .onFocusChanged {
                isFocused = it.isFocused
                if (isFocused && !error && !correct) {
                    state = EditTextState.Selected
                }
            },
        value = input,
        onValueChange = { fieldValue ->
            onValueChange?.let {
                it(fieldValue.text)
            }
            input = fieldValue
        },
        textStyle = TextStyle(
            fontFamily = montserrat,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium
        ),
        singleLine = true,
        enabled = enabled,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = { innerTextField ->
            when (state) {
                EditTextState.Default -> {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(LightGray)
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .height(componentHeight),
                        verticalAlignment = verticalAlignment,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (input.text.isNotEmpty()) {
                            innerTextField()
                        } else {
                            label?.let { it() }
                        }
                        if (trailingComponent != null) {
                            trailingComponent()
                        }
                    }
                }
                EditTextState.Selected -> {
                    Row(
                        modifier = Modifier
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .padding(start = 16.dp, end = 6.dp, top = 4.dp, bottom = 4.dp)
                            .height(componentHeight),
                        verticalAlignment = verticalAlignment,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        innerTextField()
                        if (trailingComponent != null) {
                            trailingComponent()
                        } else {
                            IconButton(
                                modifier = Modifier.size(30.dp),
                                onClick = { input = TextFieldValue("") }
                            ) {
                                Icon(
                                    modifier = Modifier.size(18.dp),
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = stringResource(id = R.string.icon_clear_content_description)
                                )
                            }
                        }
                    }
                }
                EditTextState.Error -> {
                    Row(
                        modifier = Modifier
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.error
                                ),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(start = 16.dp, end = 6.dp, top = 4.dp, bottom = 4.dp)
                            .height(componentHeight),
                        verticalAlignment = verticalAlignment,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        innerTextField()
                        IconButton(
                            modifier = Modifier.size(30.dp),
                            onClick = { input = TextFieldValue("") }
                        ) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = Icons.Rounded.Close,
                                contentDescription = stringResource(id = R.string.icon_clear_content_description),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
                EditTextState.Correct -> {
                    Row(
                        modifier = Modifier
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = Green
                                ),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(start = 16.dp, end = 6.dp, top = 4.dp, bottom = 4.dp)
                            .height(componentHeight),
                        verticalAlignment = verticalAlignment,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        innerTextField()
                        IconButton(
                            modifier = Modifier.size(30.dp),
                            enabled = false,
                            onClick = { }
                        ) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = Icons.Rounded.Check,
                                contentDescription = stringResource(id = R.string.icon_check_content_description),
                                tint = Green
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewEditText() {
    ProteKTTheme {
        EditText(
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { },
            label = {
                Text(
                    text = "label",
                    color = DarkGray,
                    fontFamily = montserrat,
                    fontWeight = FontWeight.Medium
                )
            }
        )
    }
}

enum class EditTextState {
    Default, Selected, Error, Correct;
}