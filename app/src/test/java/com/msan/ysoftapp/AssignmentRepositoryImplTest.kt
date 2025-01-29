package com.msan.ysoftapp

import app.cash.turbine.test
import com.msan.ysoftapp.data.AssignmentDao
import com.msan.ysoftapp.data.entity.AssignmentEntity
import com.msan.ysoftapp.data.mapper.toAssignment
import com.msan.ysoftapp.data.mapper.toAssignmentEntity
import com.msan.ysoftapp.data.repository.AssignmentRepositoryImpl
import com.msan.ysoftapp.domain.model.Assignment
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.text.SimpleDateFormat
import java.util.Locale

class AssignmentRepositoryImplTest {

    private lateinit var dao: AssignmentDao
    private lateinit var repository: AssignmentRepositoryImpl
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val startDate =  dateFormat.parse("2024-01-01")


    @Before
    fun setup() {
        dao = mock() // Mock the DAO
        repository = AssignmentRepositoryImpl(dao)
    }

    @Test
    fun `insertAssignment calls DAO insert`() = runTest {

        val assignment = Assignment(
            teacherId = 100,
            courseId = 200,
            assistantId = 300,
            name = "Test Assignment",
            details = "Test Details",
            marks = 10,
            recurrence = "None",
            startDate = startDate!!,
            timeAllowed = 60,
            difficulty = "Easy"
        )

        repository.insertAssignments(arrayListOf(assignment))

        verify(dao).insertAssignment(assignment.toAssignmentEntity())
    }

    @Test
    fun `deleteAssignment calls DAO delete`() = runTest {
        val assignment = Assignment(
            teacherId = 100,
            courseId = 200,
            assistantId = 300,
            name = "Test Assignment",
            details = "Test Details",
            marks = 10,
            recurrence = "None",
            startDate = startDate!!,
            timeAllowed = 60,
            difficulty = "Easy"
        )




        val assignmentEntity = assignment.toAssignmentEntity()

        repository.deleteAssignment(assignment)


        verify(dao).deleteAssignmentById(assignmentEntity.id)
    }

    @Test
    fun `updateAssignment calls DAO update`() = runTest {
        val assignment = Assignment(
            teacherId = 100,
            courseId = 200,
            assistantId = 300,
            name = "Test Assignment",
            details = "Test Details",
            marks = 10,
            recurrence = "None",
            startDate = startDate!!,
            timeAllowed = 60,
            difficulty = "Easy"
        )

        repository.updateAssignment(assignment)

        verify(dao).updateAssignment(assignment.toAssignmentEntity())
    }

    @Test
    fun `getAllAssignments transforms DAO flow into domain model`() = runTest {
        val entityList = listOf(
            mockAssignmentEntity(1),
            mockAssignmentEntity(2)
        )
        val domainList = entityList.map { it.toAssignment() }

        whenever(dao.getAllAssignments()).thenReturn(flowOf(entityList))

        repository.getAllAssignments().test {
            assertEquals(domainList, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAssignmentsForDate transforms DAO flow into domain model`() = runTest {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startDate = dateFormat.parse("2024-01-01")!! // Convert to Long here

        val entityList = listOf(
            mockAssignmentEntity(1),
            mockAssignmentEntity(2)
        )
        val domainList = entityList.map { it.toAssignment() }

        whenever(dao.getAssignmentsForDate(startDate)).thenReturn(flowOf(entityList))

        repository.getAssignmentsForDate(startDate).test {
            assertEquals(domainList, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAssignmentById transforms DAO result into domain model`() = runTest {
        val id = 1L
        val entity = mockAssignmentEntity(id)
        val domain = entity.toAssignment()

        whenever(dao.getAssignmentById(id)).thenReturn(entity)

        val result = repository.getAssignmentById(id)
        assertEquals(domain, result)
    }

    // Helper method to create mock entities
    private fun mockAssignmentEntity(id: Long) = AssignmentEntity(
        teacherId = 100,
        courseId = 200,
        assistantId = 300,
        name = "Assignment $id",
        details = "Details for Assignment $id",
        marks = 10,
        recurrence = "None",
        startDate = dateFormat.parse("2024-01-01")!!, // Convert to Long here
        timeAllowed = 60,
        difficulty = "Easy"
    )
}
