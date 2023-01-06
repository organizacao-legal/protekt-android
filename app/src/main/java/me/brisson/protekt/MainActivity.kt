package me.brisson.protekt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.brisson.protekt.ui.theme.ProteKTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProteKTTheme {
                AppNavGraph()
            }
        }
    }
}