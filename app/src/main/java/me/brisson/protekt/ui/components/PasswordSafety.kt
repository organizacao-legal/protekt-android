package me.brisson.protekt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.brisson.protekt.R
import me.brisson.protekt.domain.model.Password
import me.brisson.protekt.ui.theme.*

@Composable
fun PasswordSafetyLinearIndicator(
    modifier: Modifier = Modifier,
    password: Password,
    backgroundColor: Color = MidGray
) {
    val percentage = password.calculatePasswordSafety()

    Row(
        modifier = modifier
            .height(3.dp)
            .clip(RoundedCornerShape(1.dp))
            .background(color = backgroundColor)
    ) {
        val coerce = percentage.coerceIn(0f, 1f)
        val weight = if (coerce > 0.8f) 1f else (coerce / 0.8f)
        val counterWeight = 1f - weight

        // Danger zone
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .weight(4f)
                .background(brush = Brush.horizontalGradient(listOf(Red, Yellow)))
        ) {
            if (weight != 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(weight = weight)
                        .background(Color.Transparent)
                )
            }
            if (counterWeight != 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(weight = counterWeight)
                        .background(color = backgroundColor)
                )
            }
        }

        // Safe zone
        val color = if (percentage >= 0.8f) Green else Color.Transparent
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(color = color)
        ) {
            // Divider
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(0.5.dp)
                    .background(color = DarkGray)
            )
        }
    }
}

@Composable
fun PasswordSafetyIndicator(
    modifier: Modifier = Modifier,
    password: Password
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val eightCharStatus = if (password.value.isEmpty()) {
            PasswordSafetyIndicatorItemStatus.NONE
        } else if (password.hasAtLeast8Char) {
            PasswordSafetyIndicatorItemStatus.CORRECT
        } else {
            PasswordSafetyIndicatorItemStatus.ERROR
        }

        val shouldContainStatus = if (password.value.isEmpty()) {
            PasswordSafetyIndicatorItemStatus.NONE
        } else if (password.hasAllCharTypes()) {
            PasswordSafetyIndicatorItemStatus.CORRECT
        } else {
            PasswordSafetyIndicatorItemStatus.ERROR
        }

        val lowerCharStatus = if (password.value.isEmpty()) {
            PasswordSafetyIndicatorItemStatus.NONE
        } else if (password.hasLower) {
            PasswordSafetyIndicatorItemStatus.CORRECT
        } else {
            PasswordSafetyIndicatorItemStatus.ERROR
        }

        val upperCharStatus = if (password.value.isEmpty()) {
            PasswordSafetyIndicatorItemStatus.NONE
        } else if (password.hasUpper) {
            PasswordSafetyIndicatorItemStatus.CORRECT
        } else {
            PasswordSafetyIndicatorItemStatus.ERROR
        }

        val numericCharStatus = if (password.value.isEmpty()) {
            PasswordSafetyIndicatorItemStatus.NONE
        } else if (password.hasNumeric) {
            PasswordSafetyIndicatorItemStatus.CORRECT
        } else {
            PasswordSafetyIndicatorItemStatus.ERROR
        }

        PasswordSafetyIndicatorItem(
            text = stringResource(id = R.string.password_indicator_8_char),
            status = eightCharStatus
        )

        PasswordSafetyIndicatorItem(
            text = stringResource(id = R.string.password_indicator_should_contain),
            status = shouldContainStatus
        )

        PasswordSafetyIndicatorItem(
            modifier = Modifier.padding(start = 20.dp),
            text = stringResource(id = R.string.password_indicator_lower_case),
            status = lowerCharStatus
        )
        PasswordSafetyIndicatorItem(
            modifier = Modifier.padding(start = 20.dp),
            text = stringResource(id = R.string.password_indicator_upper_case),
            status = upperCharStatus
        )
        PasswordSafetyIndicatorItem(
            modifier = Modifier.padding(start = 20.dp),
            text = stringResource(id = R.string.password_indicator_numeric),
            status = numericCharStatus
        )

    }
}

@Composable
fun PasswordSafetyIndicatorItem(
    modifier: Modifier = Modifier,
    status: PasswordSafetyIndicatorItemStatus = PasswordSafetyIndicatorItemStatus.NONE,
    text: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val textColor = when (status) {
            PasswordSafetyIndicatorItemStatus.ERROR -> {
                Red
            }
            PasswordSafetyIndicatorItemStatus.CORRECT -> {
                Green
            }
            else -> {
                MaterialTheme.colorScheme.onBackground
            }
        }

        val iconSize = 12.dp
        when (status) {
            PasswordSafetyIndicatorItemStatus.NONE -> {
                Icon(
                    modifier = modifier
                        .size(iconSize)
                        .clip(CircleShape)
                        .background(MidGray),
                    tint = Color.White,
                    imageVector = Icons.Rounded.Close,
                    contentDescription = stringResource(id = R.string.icon_close_content_description)
                )
            }
            PasswordSafetyIndicatorItemStatus.ERROR -> {
                Icon(
                    modifier = modifier
                        .size(iconSize)
                        .clip(CircleShape)
                        .background(Red),
                    tint = Color.White,
                    imageVector = Icons.Rounded.Close,
                    contentDescription = stringResource(id = R.string.icon_close_content_description)
                )
            }
            PasswordSafetyIndicatorItemStatus.WARNING -> {
                Icon(
                    modifier = modifier
                        .size(iconSize)
                        .clip(CircleShape)
                        .background(Yellow),
                    tint = Color.White,
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = stringResource(id = R.string.icon_warning_content_description)
                )
            }
            PasswordSafetyIndicatorItemStatus.CORRECT -> {
                Icon(
                    modifier = modifier
                        .size(iconSize)
                        .clip(CircleShape)
                        .background(Green),
                    tint = Color.White,
                    imageVector = Icons.Rounded.Check,
                    contentDescription = stringResource(id = R.string.icon_check_content_description)
                )
            }
        }

        Text(
            text,
            style = TextStyle(
                fontFamily = montserrat,
                fontSize = 12.sp,
                color = textColor,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Preview
@Composable
fun PreviewPasswordSafetyLinearIndicator() {
    ProteKTTheme {
        Column {
            PasswordSafetyLinearIndicator(
                modifier = Modifier.fillMaxWidth(),
                password = Password.generateRandomPassword()
            )
        }
    }
}

@Preview
@Composable
fun PreviewPasswordSafety() {
    ProteKTTheme {
        PasswordSafetyIndicator(password = Password.generateRandomPassword())
    }
}

enum class PasswordSafetyIndicatorItemStatus {
    NONE, ERROR, WARNING, CORRECT
}