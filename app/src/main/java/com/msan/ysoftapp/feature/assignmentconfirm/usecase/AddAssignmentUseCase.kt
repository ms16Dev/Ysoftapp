package com.msan.ysoftapp.feature.assignmentconfirm.usecase

import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.domain.repository.AssignmentRepository
import javax.inject.Inject


class AddAssignmentUseCase @Inject constructor(
    private val repository: AssignmentRepository
) {

    suspend fun addAssignment(assignment: Assignment) {
        repository.insertAssignment(assignment)
    }
}


