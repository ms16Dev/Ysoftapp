package com.msan.ysoftapp.data.mapper

import com.msan.ysoftapp.data.entity.AssignmentEntity
import com.msan.ysoftapp.domain.model.Assignment


fun AssignmentEntity.toAssignment(): Assignment {
    return Assignment(
        id = id,
        teacherId = teacherId,
        courseId = courseId,
        assistantId = assistantId,
        name = name,
        details = details,
        marks = marks,
        recurrence = recurrence,
        startDate = startDate,
        timeAllowed = timeAllowed,
        difficulty = difficulty,
    )
}

fun Assignment.toAssignmentEntity(): AssignmentEntity {
    return AssignmentEntity(
        id = id,
        teacherId = teacherId,
        courseId = courseId,
        assistantId = assistantId,
        name = name,
        details = details,
        marks = marks,
        recurrence = recurrence,
        startDate = startDate,
        timeAllowed = timeAllowed,
        difficulty = difficulty,
    )
}