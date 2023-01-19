package me.brisson.protekt.presentation.create.credential

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.brisson.protekt.R
import me.brisson.protekt.ui.AppButtonOutlined
import me.brisson.protekt.ui.EditText
import me.brisson.protekt.ui.theme.DarkGray
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.ui.theme.montserrat

@Composable
fun CreateCredentialScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateCredentialViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        IconButton(modifier = Modifier.padding(start = 12.dp, top = 5.dp), onClick = onBack) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIos,
                contentDescription = stringResource(id = R.string.icon_arrow_back_content_description)
            )
        }

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
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 32.dp),
            text = "URL",
            style = TextStyle(
                fontFamily = montserrat,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = uiState.credential?.url,
            label = {
                Text(
                    text = "ex. http://google.com",
                    style = TextStyle(fontFamily = montserrat, color = DarkGray)
                )
            },
            onValueChange = { viewModel.validateUrl(it) },
            correct = uiState.urlCorrect,
            error = uiState.urlError
        )

        Text(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp),
            text = "Name",
            style = TextStyle(
                fontFamily = montserrat,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = uiState.credential?.name,
            label = {
                Text(
                    text = "ex. Google",
                    style = TextStyle(fontFamily = montserrat, color = DarkGray)
                )
            },
            onValueChange = { viewModel.validateUrl(it) }
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 16.dp),
            text = "Username",
            style = TextStyle(
                fontFamily = montserrat,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        EditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = uiState.credential?.username,
            onValueChange = { viewModel.validateUrl(it) }
        )

        Text(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp),
            text = "Password",
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
                text = uiState.credential?.password,
                onValueChange = { viewModel.validateUrl(it) },
                trailingComponent = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            painter = painterResource(id = trailingIconId),
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = visualTransformation
            )

            AppButtonOutlined(
                modifier = Modifier,
                onClick = { /*todo*/ }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }

    }
}

@Preview
@Composable
fun CreateCredentialScreenPreview() {
    ProteKTTheme {
        CreateCredentialScreen(onBack = { })
    }
}