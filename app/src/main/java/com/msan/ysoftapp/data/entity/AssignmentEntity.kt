package com.msan.ysoftapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class AssignmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val teacherId: Int,
    val courseId: Int,
    val assistantId: Int,
    val name: String,
    val details: String,
    val marks: Int,
    val recurrence: String,
    val startDate: Date,
    val timeAllowed: Int,
    val difficulty: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AssignmentEntity) return false

        return teacherId == other.teacherId &&
                courseId == other.courseId &&
                assistantId == other.assistantId &&
                name == other.name &&
                details == other.details &&
                marks == other.marks &&
                recurrence == other.recurrence &&
                startDate == other.startDate &&
                timeAllowed == other.timeAllowed &&
                difficulty == other.difficulty
    }

    override fun hashCode(): Int {
        return listOf(
            teacherId,
            courseId,
            assistantId,
            name,
            details,
            marks,
            recurrence,
            startDate,
            timeAllowed,
            difficulty
        ).hashCode()
    }
}