package com.msan.ysoftapp.domain.repository

import com.msan.ysoftapp.domain.model.Assignment
import kotlinx.coroutines.flow.Flow

interface AssignmentRepository {

    suspend fun insertAssignment(assignment: Assignment)

    suspend fun deleteAssignment(assignment: Assignment)

    suspend fun updateAssignment(assignment: Assignment)

    fun getAllAssignments(): Flow<List<Assignment>>


    fun getAssignmentsForDate(date: String): Flow<List<Assignment>>

    suspend fun getAssignmentById(id: Long): Assignment?


}