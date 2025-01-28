package com.msan.ysoftapp.feature.addassignment.viewmodel

import androidx.lifecycle.ViewModel
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.feature.addassignment.model.CourseInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddAssignmentViewModel @Inject constructor() : ViewModel() {

    private val courseInfo = CourseInformation(Calendar.getInstance().apply {
        set(2025, Calendar.JANUARY, 1) // Start Date: Jan 1, 2025
    }, teacherId = 450, courseId = 101, assistantId = 10)

    fun createAssignments(
        assignmentName: String,
        assignmentDetails: String,
        assignmentMarks: Int,
        recurrence: String,
        startDate: Date,
        timeAllowed: Int,
        difficulty: String,
    ): List<Assignment> {
        val endDate = courseInfo.courseEndDate

        // Determine the recurrence interval in days
        val interval = when (recurrence) {
            "None" -> 0 // Single assignment
            "Weekly" -> 7
            "BiWeekly" -> 14
            "Monthly" -> 30
            else -> throw IllegalArgumentException("Invalid recurrence: $recurrence")
        }

        // Initialize the calendar with the start date
        val calendar = Calendar.getInstance()
        calendar.time = startDate

        // Generate assignments up to the course end date
        val assignments = mutableListOf<Assignment>()
        if (interval == 0){

            val assignment = Assignment(
                teacherId = courseInfo.teacherId,
                courseId = courseInfo.courseId, // Assuming `courseId` exists in `CourseInformation`
                assistantId = courseInfo.assistantId, // Assuming `assistantId` exists in `CourseInformation`
                name = assignmentName,
                details = assignmentDetails,
                marks = assignmentMarks,
                recurrence = recurrence,
                startDate = calendar.time,
                timeAllowed = timeAllowed,
                difficulty = difficulty,
            )
            assignments.add(assignment)


        }else{

            while (calendar.time <= endDate) {
                val assignment = Assignment(
                    teacherId = courseInfo.teacherId,
                    courseId = courseInfo.courseId, // Assuming `courseId` exists in `CourseInformation`
                    assistantId = courseInfo.assistantId, // Assuming `assistantId` exists in `CourseInformation`
                    name = assignmentName,
                    details = assignmentDetails,
                    marks = assignmentMarks,
                    recurrence = recurrence,
                    startDate = calendar.time,
                    timeAllowed = timeAllowed,
                    difficulty = difficulty,
                )
                assignments.add(assignment)
                calendar.add(Calendar.DAY_OF_YEAR, interval)
            }
        }

        return assignments
    }
}
