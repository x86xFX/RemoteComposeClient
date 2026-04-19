package com.theek.rc.presentation.state

import androidx.compose.runtime.Stable

@Stable
sealed interface RemoteViewState {
    data object Idle : RemoteViewState
    data object Loading : RemoteViewState
    data class Success(val bytes: ByteArray) : RemoteViewState {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Success

            return bytes.contentEquals(other.bytes)
        }
        override fun hashCode(): Int {
            return bytes.contentHashCode()
        }
    }
    data class Error(val message: String?) : RemoteViewState
}