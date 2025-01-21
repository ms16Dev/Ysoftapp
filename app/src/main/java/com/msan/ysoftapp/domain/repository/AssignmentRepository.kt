package com.msan.ysoftapp.domain.repository

import com.msan.ysoftapp.data.AssignmentDao
import com.msan.ysoftapp.domain.model.Assignment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AssignmentRepository {

    suspend fun insertAssignment(assignment: Assignment)

    suspend fun deleteAssignment(assignment: Assignment)

    fun getAssignmentsForDate(localDate: LocalDate): Flow<List<AssignmentDao>>
    
}