package com.theek.rc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theek.rc.domain.repository.RemoteViewRepository
import com.theek.rc.presentation.state.RemoteViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val remoteViewRepository: RemoteViewRepository
) : ViewModel() {

    private val _removeViewState: MutableStateFlow<RemoteViewState> = MutableStateFlow(RemoteViewState.Idle)
    val removeViewState = _removeViewState.asStateFlow()

    init { getSubscriptionUiData() }

    fun getSubscriptionUiData() {
        viewModelScope.launch {
            _removeViewState.value = RemoteViewState.Loading

            val result = remoteViewRepository.getSubscriptionUi()
            result.fold(
                onFailure = { error ->
                    _removeViewState.value = RemoteViewState.Error(error.localizedMessage)
                },
                onSuccess = { bytes ->
                    _removeViewState.value = RemoteViewState.Success(bytes)
                }
            )
        }
    }
}