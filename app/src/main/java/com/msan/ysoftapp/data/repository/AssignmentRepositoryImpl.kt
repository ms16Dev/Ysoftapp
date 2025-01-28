package com.msan.ysoftapp.data.repository

import com.msan.ysoftapp.data.AssignmentDao
import com.msan.ysoftapp.data.mapper.toAssignment
import com.msan.ysoftapp.data.mapper.toAssignmentEntity
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.domain.repository.AssignmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Date


class AssignmentRepositoryImpl(
    private val dao: AssignmentDao
) : AssignmentRepository {

    override suspend fun insertAssignments(assignments: List<Assignment>): Flow<List<Assignment>> = flow {
        val savedIds = assignments.map { assignment ->
                dao.insertAssignment(assignment.toAssignmentEntity())
        }
        // Get the saved assignments with their IDs
        val savedAssignments = assignments.mapIndexed { index, assignment ->
            assignment.copy(id = savedIds[index])
        }
        emit(savedAssignments)

    }

    override suspend fun deleteAssignment(assignment: Assignment) {

        val assignmentEntity = assignment.toAssignmentEntity()

        dao.deleteAssignmentById(assignmentEntity.id)
    }

    override suspend fun updateAssignment(assignment: Assignment) {
        dao.updateAssignment(assignment.toAssignmentEntity())
    }

    override fun getAllAssignments(): Flow<List<Assignment>> {
        return dao.getAllAssignments().map { entities ->
            entities.map { it.toAssignment() }
        }
    }


    override fun getAssignmentsForDate(date: Date): Flow<List<Assignment>> {
        return dao.getAssignmentsForDate(
            date = date
        ).map { entities ->
            entities.map { it.toAssignment() }
        }
    }

    override suspend fun getAssignmentById(id: Long): Assignment? {
        return dao.getAssignmentById(id)?.toAssignment()
    }


}