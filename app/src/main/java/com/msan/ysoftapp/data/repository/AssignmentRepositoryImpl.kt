package com.msan.ysoftapp.data.repository

import com.msan.ysoftapp.data.AssignmentDao
import com.msan.ysoftapp.data.mapper.toAssignment
import com.msan.ysoftapp.data.mapper.toAssignmentEntity
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.domain.repository.AssignmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class AssignmentRepositoryImpl(
    private val dao: AssignmentDao
): AssignmentRepository {

    override suspend fun insertAssignment(assignment: Assignment) {
        dao.insertAssignment(assignment.toAssignmentEntity())
    }

    override suspend fun deleteAssignment(assignment: Assignment) {

        val assignmentEntity = assignment.toAssignmentEntity()

        // Assuming you have access to the `id`, pass it to the DAO method
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




    override fun getAssignmentsForDate(date: Long): Flow<List<Assignment>> {
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