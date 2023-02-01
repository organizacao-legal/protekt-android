package me.brisson.protekt.presentation.item_detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.brisson.protekt.R
import me.brisson.protekt.domain.model.Credential
import me.brisson.protekt.ui.AppButton
import me.brisson.protekt.ui.AppButtonOutlined
import me.brisson.protekt.ui.EditText
import me.brisson.protekt.ui.PasswordSafetyLinearIndicator
import me.brisson.protekt.ui.theme.DarkGray
import me.brisson.protekt.ui.theme.MidGray
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.ui.theme.montserrat

@Composable
fun CredentialDetails(
    modifier: Modifier = Modifier,
    credential: Credential
) {
    val context = LocalContext.current
    val localClipboardManager = LocalClipboardManager.current
    var showPassword by remember { mutableStateOf(false) }
    var usernameInput by remember { mutableStateOf(TextFieldValue("")) }
    var passwordInput by remember { mutableStateOf(TextFieldValue("")) }
    var notesInput by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            AsyncImage(
                modifier = Modifier
                    .height(60.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp)),
                model = credential.image,
                contentDescription = stringResource(
                    id = R.string.credential_image_content_description,
                    credential.name
                ),
                placeholder = painterResource(id = R.drawable.image_placeholder),
                error = painterResource(id = R.drawable.image_placeholder)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = credential.name,
                    style = TextStyle(
                        fontFamily = montserrat,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(CircleShape)
                        .clickable { }
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = credential.url,
                        style = TextStyle(
                            fontFamily = montserrat,
                            fontSize = 13.sp,
                            color = DarkGray,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Icon(
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .size(14.dp),
                        painter = painterResource(id = R.drawable.ic_external_link),
                        tint = DarkGray,
                        contentDescription = null
                    )
                }

            }
        }

        Spacer(Modifier.padding(top = 32.dp))
        Text(
            text = "Username",
            style = TextStyle(
                fontFamily = montserrat,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        EditText(
            value = usernameInput,
            onValueChange = { usernameInput = it },
            onClearValue = { usernameInput = TextFieldValue("") },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "username",
                    style = TextStyle(fontFamily = montserrat, color = DarkGray)
                )
            },
            enabled = false,
            trailingComponent = {
                IconButton(
                    modifier = Modifier.size(36.dp),
                    onClick = {
                        Toast.makeText(context, "Username copied", Toast.LENGTH_SHORT).show()
                        localClipboardManager.setText(AnnotatedString(credential.username))
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        tint = DarkGray,
                        imageVector = Icons.Rounded.ContentCopy,
                        contentDescription = null
                    )
                }
            }
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Password",
            style = TextStyle(
                fontFamily = montserrat,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        EditText(
            value = passwordInput,
            onValueChange = { passwordInput = it },
            onClearValue = { passwordInput = TextFieldValue("") },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "password",
                    style = TextStyle(fontFamily = montserrat, color = DarkGray)
                )
            },
            enabled = false,
            visualTransformation = if (!showPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingComponent = {
                Row {
                    val painter = painterResource(
                        id = if (showPassword) {
                            R.drawable.ic_eye
                        } else {
                            R.drawable.ic_eye_off
                        }
                    )
                    IconButton(
                        modifier = Modifier.size(36.dp),
                        onClick = { showPassword = !showPassword }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            tint = DarkGray,
                            painter = painter,
                            contentDescription = null
                        )
                    }

                    IconButton(
                        modifier = Modifier.size(36.dp),
                        onClick = {
                            Toast.makeText(context, "Password copied", Toast.LENGTH_SHORT).show()
                            localClipboardManager.setText(AnnotatedString(credential.password))
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            tint = DarkGray,
                            imageVector = Icons.Rounded.ContentCopy,
                            contentDescription = null
                        )
                    }
                }
            }
        )

        PasswordSafetyLinearIndicator(percentage = credential.calculatePasswordSafety())

        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = "Notes",
            style = TextStyle(
                fontFamily = montserrat,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        EditText(
            value = notesInput,
            onValueChange = { notesInput = it },
            onClearValue = { notesInput = TextFieldValue("") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            verticalAlignment = Alignment.Top,
            enabled = false,
        )
    }
}

@Composable
fun ItemOptionsDropdown(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    DropdownMenu(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = stringResource(id = R.string.icon_edit_content_description),
                        tint = DarkGray
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = R.string.edit),
                        style = TextStyle(
                            fontFamily = montserrat,
                            color = DarkGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            },
            onClick = onEdit
        )

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = MidGray
        )

        DropdownMenuItem(
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = stringResource(id = R.string.icon_delete_content_description),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = R.string.delete),
                        style = TextStyle(
                            fontFamily = montserrat,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            },
            onClick = onDelete
        )
    }
}

@Composable
fun DeleteItemDialogUi(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onCancel: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = onCancel) {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = stringResource(id = R.string.icon_clear_content_description)
            )
        }

        Column(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp, top = 24.dp),
        ) {
            Text(
                text = stringResource(id = R.string.delete_item_dialog_title),
                style = TextStyle(
                    fontFamily = montserrat,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.delete_item_dialog_text),
                style = TextStyle(
                    fontFamily = montserrat,
                    fontSize = 12.sp,
                    color = DarkGray
                )
            )

            Row(modifier = Modifier.padding(top = 30.dp)) {
                val middlePaddingValue = 8.dp
                AppButtonOutlined(
                    modifier = Modifier
                        .padding(end = middlePaddingValue)
                        .weight(1f),
                    onClick = onCancel,
                    borderColor = DarkGray,
                ) {
                    Text(
                        stringResource(id = R.string.delete_item_dialog_cancel_button),
                        fontFamily = montserrat,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = DarkGray
                    )
                }
                AppButton(
                    modifier = Modifier
                        .padding(start = middlePaddingValue)
                        .weight(1f),
                    onClick = onDelete,
                    buttonColor = MaterialTheme.colorScheme.error
                ) {
                    Text(
                        stringResource(id = R.string.delete_item_dialog_delete_button),
                        fontFamily = montserrat,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemOptionsDropdown() {
    ProteKTTheme {
        ItemOptionsDropdown(
            modifier = Modifier.width(150.dp),
            expanded = true,
            onDismissRequest = { },
            onDelete = { },
            onEdit = { },
        )
    }
}

@Preview
@Composable
fun PreviewDeleteItemDialogUi() {
    ProteKTTheme {
        DeleteItemDialogUi(onDelete = { }, onCancel = { })
    }
}

@Preview
@Composable
fun PreviewCredentialDetails() {
    ProteKTTheme {
        CredentialDetails(
            credential = Credential(
                image = "https://logo.clearbit.com/https://twitter.com",
                name = "Twitter",
                username = "@JonDoe",
                password = "@a123",
                url = "http://twitter.com"
            )
        )
    }
}