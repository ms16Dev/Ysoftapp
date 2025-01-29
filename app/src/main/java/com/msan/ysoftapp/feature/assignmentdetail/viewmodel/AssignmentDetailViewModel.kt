package com.msan.ysoftapp.feature.assignmentdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.feature.home.usecase.UpdateAssignmentUseCase
import com.msan.ysoftapp.feature.assignmentdetail.usecase.GetAssignmentUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignmentDetailViewModel @Inject constructor(
    private val getAssignmentUseCase: GetAssignmentUseCase,
    private val updateAssignmentUseCase: UpdateAssignmentUseCase,
) : ViewModel() {
    private val _assignment = MutableStateFlow<Assignment?>(null)
    val assignment = _assignment.asStateFlow()

    fun getAssignmentById(id: Long) {
        viewModelScope.launch {
            _assignment.value = getAssignmentUseCase(id)
        }
    }


}
