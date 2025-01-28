package com.msan.ysoftapp.domain.repository

import com.msan.ysoftapp.domain.model.Assignment
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface AssignmentRepository {

    suspend fun insertAssignments(assignments: List<Assignment>): Flow<List<Assignment>>

    suspend fun deleteAssignment(assignment: Assignment)

    suspend fun updateAssignment(assignment: Assignment)

    fun getAllAssignments(): Flow<List<Assignment>>


    fun getAssignmentsForDate(date: Date): Flow<List<Assignment>>

    suspend fun getAssignmentById(id: Long): Assignment?


}