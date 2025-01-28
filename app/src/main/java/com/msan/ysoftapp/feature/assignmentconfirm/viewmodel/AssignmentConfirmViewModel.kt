package com.msan.ysoftapp.feature.assignmentconfirm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msan.ysoftapp.feature.assignmentconfirm.usecase.AddAssignmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignmentConfirmViewModel @Inject constructor(
    private val addAssignmentUseCase: AddAssignmentUseCase
   
):ViewModel(){
    private val _isAssignmentSaved = MutableSharedFlow<Unit>()
    val isAssignmentSaved = _isAssignmentSaved.asSharedFlow()
    
    fun addAssignment(state: AssignmentConfirmState){
        viewModelScope.launch {
            val assignments = state.assignments
            addAssignmentUseCase.addAssignment(assignments).collect { savedAssignments ->
                // Schedule notifications for saved assignments that have proper IDs
                savedAssignments.forEach { assignment ->
                    Log.d("AssignmentConfirmViewModel", "Scheduling notification for assignment: $assignment")
                }
                _isAssignmentSaved.emit(Unit)
            }
        }
    }
}