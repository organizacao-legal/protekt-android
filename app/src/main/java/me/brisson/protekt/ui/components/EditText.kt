package me.brisson.protekt.ui.components

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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.brisson.protekt.R
import me.brisson.protekt.ui.theme.*

@Composable
fun EditText(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onClearValue: () -> Unit,
    onFocus: ((hasFocus: Boolean) -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    error: Boolean = false,
    correct: Boolean = false,
    label: (@Composable RowScope.() -> Unit)? = null,
    trailingComponent: (@Composable RowScope.() -> Unit)? = null,
) {
    var isFocused by remember { mutableStateOf(false) }


    val componentHeight = 50.dp

    BasicTextField(
        modifier = modifier
            .height(componentHeight)
            .onFocusChanged {
                isFocused = it.isFocused
                onFocus?.invoke(it.hasFocus)
            },
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontFamily = montserrat,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium
        ),
        singleLine = singleLine,
        enabled = enabled,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = { innerTextField ->
            val backgroundColor = if (!isFocused && !error && !correct) {
                LightGray
            } else {
                Color.Transparent
            }

            val borderColor =
                if (isFocused) {
                    LightBlue
                } else if (error) {
                    Red
                } else if (correct) {
                    Green
                } else {
                    Color.Transparent
                }

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = borderColor
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .background(backgroundColor)
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = verticalAlignment,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp)
                        .fillMaxHeight(),
                    verticalAlignment = verticalAlignment,
                ) {
                    if (value.text.isNotEmpty() || isFocused) {
                        innerTextField()
                    } else {
                        label?.let { it() }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = verticalAlignment
                ) {
                    trailingComponent?.let { it() }
                    if (isFocused) {
                        IconButton(
                            modifier = Modifier.size(30.dp),
                            onClick = onClearValue
                        ) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = Icons.Rounded.Close,
                                tint = DarkGray,
                                contentDescription = stringResource(id = R.string.icon_clear_content_description)
                            )
                        }
                    } else if (correct) {
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .size(18.dp),
                            tint = Green,
                            imageVector = Icons.Rounded.Check,
                            contentDescription = stringResource(id = R.string.icon_check_content_description)
                        )
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
        Column(Modifier.padding(20.dp)) {
            var error by remember { mutableStateOf(false) }
            var correct by remember { mutableStateOf(false) }
            val focusManager = LocalFocusManager.current
            var firstInput by remember { mutableStateOf(TextFieldValue("")) }
            var secondInput by remember { mutableStateOf(TextFieldValue("")) }

            var showPassword by remember { mutableStateOf(false) }
            val trailingIconId = if (showPassword) R.drawable.ic_eye else R.drawable.ic_eye_off
            val visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }

            EditText(
                value = firstInput,
                onValueChange = { firstInput = it },
                onClearValue = { firstInput = TextFieldValue("") },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "email",
                        color = DarkGray,
                        fontFamily = montserrat,
                        fontWeight = FontWeight.Medium
                    )
                },
                error = error,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                    error = true
                })
            )
            EditText(
                value = secondInput,
                onValueChange = { secondInput = it },
                onClearValue = { secondInput = TextFieldValue("") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                label = {

                },
                correct = correct,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    correct = true
                }),
                visualTransformation = visualTransformation,
                trailingComponent = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            painter = painterResource(id = trailingIconId),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}
