package com.msan.ysoftapp.feature.assignmentconfirm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msan.ysoftapp.feature.assignmentconfirm.usecase.AddAssignmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignmentConfirmViewModel @Inject constructor(
    private val addAssignmentUseCase: AddAssignmentUseCase

):ViewModel(){
    fun addAssignment(state: AssignmentConfirmState){
        viewModelScope.launch {
            val assignment = state.assignment
            addAssignmentUseCase.addAssignment(assignment)

        }
    }
}