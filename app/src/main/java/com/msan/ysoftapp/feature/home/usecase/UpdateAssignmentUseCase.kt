package com.msan.ysoftapp.feature.home.usecase

import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.domain.repository.AssignmentRepository
import javax.inject.Inject

class UpdateAssignmentUseCase @Inject constructor(
    private val repository: AssignmentRepository
) {

    suspend fun updateAssignment(medication: Assignment) {
        return repository.updateAssignment(medication)
    }
}

