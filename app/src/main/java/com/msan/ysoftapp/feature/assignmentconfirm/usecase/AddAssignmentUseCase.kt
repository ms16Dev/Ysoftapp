package com.msan.ysoftapp.feature.assignmentconfirm.usecase

import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.domain.repository.AssignmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AddAssignmentUseCase @Inject constructor(
    private val repository: AssignmentRepository
) {

    suspend fun addAssignment(assignments: List<Assignment>): Flow<List<Assignment>> =  repository.insertAssignments(assignments)
    }



