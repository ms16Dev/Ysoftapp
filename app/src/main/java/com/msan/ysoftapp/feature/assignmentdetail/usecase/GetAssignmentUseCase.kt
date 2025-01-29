package com.msan.ysoftapp.feature.assignmentdetail.usecase

import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.domain.repository.AssignmentRepository

import javax.inject.Inject

class GetAssignmentUseCase @Inject constructor(
    private val repository: AssignmentRepository
) {
    suspend operator fun invoke(id: Long): Assignment? = repository.getAssignmentById(id)
}
