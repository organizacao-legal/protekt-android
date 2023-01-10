package me.brisson.protekt.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.brisson.protekt.ui.theme.*

@Composable
fun PasswordSafetyLinearIndicator(
    modifier: Modifier = Modifier,
    percentage: Float,
    backgroundColor: Color = MiddleGray
) {
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
            if(weight != 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(weight = weight)
                        .background(Color.Transparent)
                )
            }
            if(counterWeight != 0f) {
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

@Preview
@Composable
fun PreviewPasswordSafety() {
    ProteKTTheme {
        Column {
            var percentage by remember { mutableStateOf(0.8f) }
            Slider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                value = percentage,
                onValueChange = { percentage = it })
            PasswordSafetyLinearIndicator(modifier = Modifier.fillMaxWidth(), percentage = percentage)
        }
    }
}