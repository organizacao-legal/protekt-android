package me.brisson.protekt.presentation.item_detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.brisson.protekt.R
import me.brisson.protekt.domain.model.Credential
import me.brisson.protekt.ui.EditText
import me.brisson.protekt.ui.PasswordSafetyLinearIndicator
import me.brisson.protekt.ui.theme.DarkGray
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
            text = credential.username,
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
            text = credential.password,
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
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            verticalAlignment = Alignment.Top,
            text = credential.note,
            enabled = false,
        )
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