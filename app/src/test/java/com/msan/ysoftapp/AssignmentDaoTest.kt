package com.msan.ysoftapp


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.msan.ysoftapp.data.AssignmentDao
import com.msan.ysoftapp.data.AssignmentDatabase
import com.msan.ysoftapp.data.entity.AssignmentEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.text.SimpleDateFormat
import java.util.Locale

@RunWith(RobolectricTestRunner::class)

class AssignmentDaoTest {

    private lateinit var database: AssignmentDatabase
    private lateinit var dao: AssignmentDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            AssignmentDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.assignmentDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `insert and retrieve assignments`() = runBlocking {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val entity = AssignmentEntity(
            id = 1,
            teacherId = 100,
            courseId = 200,
            assistantId = 300,
            name = "Math Assignment",
            details = "Solve the equations",
            marks = 10,
            recurrence = "Weekly",
            startDate = dateFormat.parse("2024-01-01")!!,
            timeAllowed = 60,
            difficulty = "Medium"
        )

        dao.insertAssignment(entity)

        val result = dao.getAssignmentsForDate("2024-01-01").first()
        Assert.assertTrue(result.contains(entity))
    }

    @Test
    fun `delete assignment`() = runBlocking {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val entity = AssignmentEntity(
            id = 1,
            teacherId = 100,
            courseId = 200,
            assistantId = 300,
            name = "Math Assignment",
            details = "Solve the equations",
            marks = 10,
            recurrence = "Weekly",
            startDate = dateFormat.parse("2024-01-01")!!,
            timeAllowed = 60,
            difficulty = "Medium"
        )

        dao.insertAssignment(entity)
        dao.deleteAssignment(entity)

        val result = dao.getAssignmentsForDate("2024-01-01").first()
        Assert.assertTrue(result.isEmpty())
    }
}