package com.msan.ysoftapp.feature.addassignment.model

import androidx.compose.runtime.saveable.Saver
import java.util.Calendar
import java.util.Date

class CourseInformation(
    private val calendar: Calendar,
    val teacherId: Int,
    val courseId: Int,
    val assistantId: Int
) {

    val courseStartDate: Date
        get() = calendar.time

    val courseEndDate: Date
        get() {
            val endCalendar = Calendar.getInstance()
            endCalendar.time = calendar.time
            endCalendar.add(Calendar.MONTH, 6) // Example: 6-month course duration
            return endCalendar.time
        }

    companion object {
        fun getStateSaver() = Saver<CourseInformation, Calendar>(
            save = { state -> state.calendar },
            restore = {
                CourseInformation(
                    it,
                    0,
                    0,
                    0
                )
            } // Placeholder values for courseId and assistantId
        )

        fun getStateListSaver() = Saver<MutableList<CourseInformation>, MutableList<Calendar>>(
            save = { state -> state.map { it.calendar }.toMutableList() },
            restore = { it -> it.map { CourseInformation(it, 0, 0,0) }.toMutableList() }
        )
    }
}
