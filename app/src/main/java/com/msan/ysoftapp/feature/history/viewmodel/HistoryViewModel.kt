package com.msan.ysoftapp.feature.history.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msan.ysoftapp.feature.home.usecase.GetAssignmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAssignmentsUseCase: GetAssignmentsUseCase
) : ViewModel() {

    var state by mutableStateOf(HistoryState())
        private set

    init {
        loadAssignments()
    }

    fun loadAssignments() {
        viewModelScope.launch {
            getAssignmentsUseCase.getAssignments().onEach { assignmentList ->
                state = state.copy(
                    assignments = assignmentList
                )
            }.launchIn(viewModelScope)
        }
    }
}
