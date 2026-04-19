package com.theek.rc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.theek.rc.presentation.screen.SubscriptionScreen
import com.theek.rc.ui.theme.RemoteComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RemoteComposeTheme {
                SubscriptionScreen(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}