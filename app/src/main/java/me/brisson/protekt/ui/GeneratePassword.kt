package me.brisson.protekt.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.brisson.protekt.R
import me.brisson.protekt.domain.model.Password
import me.brisson.protekt.domain.model.Password.Companion.generateRandomPassword
import me.brisson.protekt.ui.theme.MidGray
import me.brisson.protekt.ui.theme.ProteKTTheme
import me.brisson.protekt.ui.theme.montserrat
import me.brisson.protekt.utils.isNumeric

@Composable
fun GeneratePassword(
    modifier: Modifier = Modifier,
    onPasswordGenerated: (password: Password) -> Unit
) {
    val context = LocalContext.current
    val localClipboardManager = LocalClipboardManager.current

    var password by remember { mutableStateOf(generateRandomPassword()) }
    onPasswordGenerated(password)

    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .border(width = 1.dp, color = MidGray, shape = RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(horizontal = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 30.dp, horizontal = 18.dp),
            text = getAnnotatedString(
                password.value.toCharArray(),
                SpanStyle(color = MaterialTheme.colorScheme.primary)
            ),
            style = TextStyle(
                fontFamily = montserrat,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(onClick = {
                password = generateRandomPassword()
                onPasswordGenerated(password)
            }) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = stringResource(id = R.string.icon_refresh_content_description)
                )
                Text(text = stringResource(id = R.string.reload), fontFamily = montserrat)
            }
            TextButton(onClick = {
                Toast.makeText(context, "Password copied", Toast.LENGTH_SHORT).show()
                localClipboardManager.setText(AnnotatedString(password.value))
            }) {
                Icon(
                    imageVector = Icons.Rounded.CopyAll,
                    contentDescription = stringResource(id = R.string.icon_copy_content_description)
                )
                Text(text = stringResource(id = R.string.copy), fontFamily = montserrat)
            }
        }
    }

}

private fun getAnnotatedString(password: CharArray, numberSpanStyle: SpanStyle): AnnotatedString {
    val builder = AnnotatedString.Builder(password.concatToString())

    // Finding the indexes where is number
    password.forEachIndexed { i, char ->
        if (char.isNumeric()) {
            builder.addStyle(numberSpanStyle, i, i + 1) // Applying style where char is number
        }
    }

    return builder.toAnnotatedString()
}

@Preview
@Composable
fun PreviewGeneratePassword() {
    ProteKTTheme {
        GeneratePassword(
            modifier = Modifier,
            onPasswordGenerated = { }
        )
    }
}
