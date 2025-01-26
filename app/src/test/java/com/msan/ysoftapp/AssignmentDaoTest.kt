package com.msan.ysoftapp


import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import com.msan.ysoftapp.data.AssignmentDao
import com.msan.ysoftapp.data.AssignmentDatabase
import com.msan.ysoftapp.data.entity.AssignmentEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
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
        ).setJournalMode(RoomDatabase.JournalMode.TRUNCATE)  // You can enable logging
        .allowMainThreadQueries().build()
        dao = database.dao

        database.clearAllTables()

    }


    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `insert and retrieve assignments`() = runBlocking {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val entity = AssignmentEntity(
            teacherId = 100,
            courseId = 200,
            assistantId = 300,
            name = "Math Assignment",
            details = "Solve the equations",
            marks = 10,
            recurrence = "Weekly",
            startDate = dateFormat.parse("2024-01-01")!!, // Convert to Long here
            timeAllowed = 60,
            difficulty = "Medium"
        )

        dao.insertAssignment(entity)
        delay(100)  // Give the database some time to commit


        val result = dao.getAssignmentsForDate(entity.startDate).first()
        println("Assignments for 2024-01-01: $result")  // Add this log statement

        val startDate = dao.getStartDateForAssignment("Math Assignment").first()
        println("Start date in database: $startDate")  // Log the startDate to verify

        Assert.assertTrue(result.contains(entity))
    }

    @Test
    fun `delete assignment`() = runBlocking {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val entity = AssignmentEntity(
            teacherId = 100,
            courseId = 200,
            assistantId = 300,
            name = "Math Assignment",
            details = "Solve the equations",
            marks = 10,
            recurrence = "Weekly",
            startDate = dateFormat.parse("2024-01-02")!!, // Convert to Long here
            timeAllowed = 60,
            difficulty = "Medium"
        )

        // Insert the assignment
        dao.insertAssignment(entity)
        delay(100) // Allow time for the insert operation to complete

        // Fetch the inserted assignment to get its ID
        val insertedAssignment = dao.getAssignmentsForDate(entity.startDate).firstOrNull()?.first()
        Assert.assertNotNull("Inserted assignment should not be null", insertedAssignment)

        // Delete the assignment using its ID
        dao.deleteAssignmentById(insertedAssignment!!.id)
        delay(100) // Allow time for the delete operation to complete

        // Verify the assignment has been deleted
        val resultAfterDelete = dao.getAssignmentsForDate(entity.startDate).first()
        println("Assignments for 2024-01-02 after delete: $resultAfterDelete") // Log the result after deletion

        Assert.assertTrue(resultAfterDelete.isEmpty())
    }

}