package com.msan.ysoftapp

import com.google.gson.Gson
import com.msan.ysoftapp.data.entity.AssignmentEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale

class AssignmentEntityTest {

    @Test
    fun `assignment entity serialization works`() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val entity = AssignmentEntity(

            teacherId = 100,
            courseId = 200,
            assistantId = 300,
            name = "Math Assignment",
            details = "Solve the equations",
            marks = 10,
            recurrence = "Weekly",
            startDate = dateFormat.parse("2024-01-01")!!.time, // Convert to Long here
            timeAllowed = 60,
            difficulty = "Medium"
        )

        val json = Gson().toJson(entity)
        val deserialized = Gson().fromJson(json, AssignmentEntity::class.java)

        assertEquals(entity, deserialized)
    }
}