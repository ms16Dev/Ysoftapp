package com.msan.ysoftapp.data.repository

import com.msan.ysoftapp.data.AssignmentDao
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.domain.repository.AssignmentRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class AssignmentRepositoryImpl(
    private val dao: AssignmentDao
): AssignmentRepository {

    override suspend fun insertAssignment(assignment: Assignment) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAssignment(assignment: Assignment) {
        TODO("Not yet implemented")
    }

    override fun getAssignmentsForDate(localDate: LocalDate): Flow<List<AssignmentDao>> {
        TODO("Not yet implemented")
    }


}