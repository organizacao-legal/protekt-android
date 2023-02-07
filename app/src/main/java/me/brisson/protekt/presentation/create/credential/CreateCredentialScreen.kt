package me.brisson.protekt.presentation.create.credential

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.brisson.protekt.R
import me.brisson.protekt.domain.model.Credential
import me.brisson.protekt.domain.model.Password
import me.brisson.protekt.ui.*
import me.brisson.protekt.ui.components.BottomSheet
import me.brisson.protekt.ui.components.GeneratePasswordBottomSheet
import me.brisson.protekt.ui.components.PasswordSafetyIndicator
import me.brisson.protekt.ui.components.PasswordSafetyLinearIndicator
import me.brisson.protekt.ui.theme.DarkGray
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.ui.theme.montserrat

@ExperimentalComposeUiApi
@Composable
fun CreateCredentialScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateCredentialViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val credential = uiState.credential

    val focusManager = LocalFocusManager.current
    var openDialog by remember { mutableStateOf(false) }

    var urlInput by remember { mutableStateOf(TextFieldValue(credential?.url ?: "")) }
    var nameInput by remember { mutableStateOf(TextFieldValue(credential?.name ?: "")) }
    var usernameInput by remember { mutableStateOf(TextFieldValue(credential?.username ?:"")) }
    var passwordInput by remember { mutableStateOf(TextFieldValue(credential?.password?.value ?: "")) }
    var notesInput by remember { mutableStateOf(TextFieldValue(credential?.note ?: "")) }

    var passwordEditTextHasFocus by remember { mutableStateOf(false) }

    uiState.postCredentialSuccess?.let { _ -> onBack() }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            if (openDialog) {
                BottomSheet(onDismissRequest = { openDialog = false }) {
                    GeneratePasswordBottomSheet(
                        onUse = { password ->
                            passwordInput = TextFieldValue(password.value)
                            openDialog = false
                        },
                        onCancel = { openDialog = false }
                    )
                }
            }
        }

        item {
            IconButton(modifier = Modifier.padding(start = 12.dp, top = 5.dp), onClick = onBack) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIos,
                    contentDescription = stringResource(id = R.string.icon_arrow_back_content_description)
                )
            }
        }

        item {
            Text(
                modifier = Modifier.padding(start = 24.dp, top = 24.dp),
                text = stringResource(id = R.string.credentials),
                style = TextStyle(
                    fontFamily = montserrat,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Text(
                modifier = Modifier.padding(start = 24.dp, top = 8.dp),
                text = "* items are mandatory",
                style = TextStyle(
                    fontFamily = montserrat,
                    color = DarkGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        item {
            Text(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 32.dp),
                text = stringResource(id = R.string.url) ,
                style = TextStyle(
                    fontFamily = montserrat,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            EditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                value = urlInput,
                onValueChange = { urlInput = it },
                onFocus = { if (!it) viewModel.validateUrl(urlInput.text) },
                onClearValue = { urlInput = TextFieldValue("") },
                label = {
                    Text(
                        text = stringResource(id = R.string.url_label),
                        style = TextStyle(fontFamily = montserrat, color = DarkGray)
                    )
                },
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                correct = uiState.isUrlValid ?: false,
                error = uiState.isUrlValid?.not() ?: false
            )
        }

        item {
            Text(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp),
                text = stringResource(id = R.string.name),
                style = TextStyle(
                    fontFamily = montserrat,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            EditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                value = nameInput,
                onValueChange = { nameInput = it },
                onFocus = { if (!it) viewModel.validateName(nameInput.text) },
                onClearValue = { nameInput = TextFieldValue("") },
                label = {
                    Text(
                        text = stringResource(id = R.string.name_label),
                        style = TextStyle(fontFamily = montserrat, color = DarkGray)
                    )
                },
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                correct = uiState.isNameValid ?: false,
                error = uiState.isNameValid?.not() ?: false
            )
        }

        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 16.dp),
                text = stringResource(id = R.string.username),
                style = TextStyle(
                    fontFamily = montserrat,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            EditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                value = usernameInput,
                onValueChange = { usernameInput = it },
                onClearValue = { usernameInput = TextFieldValue("") },
                onFocus = { if (!it) viewModel.validateUsername(usernameInput.text) },
                label = {
                    Text(
                        text = stringResource(id = R.string.username_label),
                        style = TextStyle(fontFamily = montserrat, color = DarkGray)
                    )
                },
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                correct = uiState.isUsernameValid ?: false,
                error = uiState.isUsernameValid?.not() ?: false
            )
        }

        item {
            Text(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp),
                text = stringResource(id = R.string.password),
                style = TextStyle(
                    fontFamily = montserrat,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Row(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var showPassword by remember { mutableStateOf(false) }
                val trailingIconId = if (showPassword) R.drawable.ic_eye else R.drawable.ic_eye_off
                val visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }

                EditText(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    value = passwordInput,
                    onValueChange = { passwordInput = it },
                    onClearValue = { passwordInput = TextFieldValue("") },
                    onFocus = {
                        passwordEditTextHasFocus = it
                        if (!it) viewModel.validatePassword(Password(passwordInput.text))
                    },
                    trailingComponent = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                painter = painterResource(id = trailingIconId),
                                tint = DarkGray,
                                contentDescription = stringResource(id = R.string.icon_toggle_eye_content_description)
                            )
                        }
                    },
                    visualTransformation = visualTransformation,
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Next
                        )
                    }),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    correct = uiState.isPasswordValid ?: false,
                    error = uiState.isPasswordValid?.not() ?: false
                )

                AppButtonOutlined(
                    modifier = Modifier,
                    onClick = { openDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = stringResource(id = R.string.icon_refresh_content_description),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            PasswordSafetyLinearIndicator(
                modifier = Modifier.padding(horizontal = 24.dp),
                password = Password(passwordInput.text)
            )

            AnimatedVisibility(
                visible = passwordEditTextHasFocus,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                PasswordSafetyIndicator(
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 8.dp),
                    password = Password(passwordInput.text)
                )
            }
        }

        item {
            Text(
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp),
                text = stringResource(id = R.string.notes),
                style = TextStyle(
                    fontFamily = montserrat,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            EditText(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(130.dp),
                value = notesInput,
                onValueChange = { notesInput = it },
                onFocus = { if (!it) viewModel.validateNote(notesInput.text) },
                onClearValue = { notesInput = TextFieldValue("") },
                verticalAlignment = Alignment.Top,
                singleLine = false,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                correct = uiState.isNoteValid ?: false,
                error = uiState.isNoteValid?.not() ?: false
            )
        }

        item {
            AppButton(modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
                onClick = {
                    // Editing existing credential
                    credential?.let {
                        it.url = urlInput.text
                        it.name = nameInput.text
                        it.username = usernameInput.text
                        it.password = Password(passwordInput.text)
                        it.note = notesInput.text

                        viewModel.createCredential(it)
                        return@AppButton
                    }

                    // Creating a new credential
                    viewModel.createCredential(Credential(
                    url = urlInput.text,
                    name = nameInput.text,
                    username = usernameInput.text,
                    password = Password(passwordInput.text),
                    note = notesInput.text
                )) }
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                    fontFamily = montserrat,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun CreateCredentialScreenPreview() {
    ProteKTTheme {
        CreateCredentialScreen(onBack = { })
    }
}