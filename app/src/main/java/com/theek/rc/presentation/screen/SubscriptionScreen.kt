package com.theek.rc.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.remote.player.compose.RemoteDocumentPlayer
import androidx.compose.remote.player.core.RemoteDocument
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.theek.rc.presentation.state.RemoteViewState
import com.theek.rc.presentation.viewmodel.SubscriptionViewModel

@SuppressLint("RestrictedApi")
@Composable
fun SubscriptionScreen(
    modifier: Modifier = Modifier,
    subscriptionViewModel: SubscriptionViewModel = hiltViewModel()
) {
    val result by subscriptionViewModel.removeViewState.collectAsStateWithLifecycle()

    Scaffold(containerColor = Color(0xFF000000)) { innerPadding ->
        when (val data = result) {
            RemoteViewState.Idle -> Unit

            is RemoteViewState.Error -> {
                Box(
                    modifier = modifier
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(paddingValues = innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = data.message ?: "Oops! Something went wrong.",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            RemoteViewState.Loading -> {
                Box(
                    modifier = modifier.padding(paddingValues = innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is RemoteViewState.Success -> {
                val parsedDocument = remember(data.bytes) { RemoteDocument(data.bytes).document }

                RemoteDocumentPlayer(
                    modifier = modifier.padding(paddingValues = innerPadding),
                    document = parsedDocument,
                    documentWidth = parsedDocument.width,
                    documentHeight = parsedDocument.height
                )
            }
        }
    }
}