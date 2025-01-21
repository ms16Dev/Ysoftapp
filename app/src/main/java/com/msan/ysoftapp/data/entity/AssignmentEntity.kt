package com.msan.ysoftapp.data.entity

import androidx.room.PrimaryKey
import java.util.*

data class AssignmentEntity(
    @PrimaryKey val id: Int,
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
)