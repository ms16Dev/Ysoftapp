package com.msan.ysoftapp.feature.home.usecase

import android.util.Log
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.domain.repository.AssignmentRepository
import com.msan.ysoftapp.extention.truncateToMidnight
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject


class GetAssignmentsUseCase @Inject constructor(
    private val repository: AssignmentRepository
) {

    fun getAssignments(date: Date? = null): Flow<List<Assignment>> {
        Log.e("GetAssignmentsUseCase", "date: $date")

        return if (date != null) {
            val midnightDate = Date().truncateToMidnight(date)
            Log.e("GetAssignmentsUseCase", "midNightDate: $midnightDate")
            repository.getAssignmentsForDate(midnightDate)
        } else repository.getAllAssignments()
    }
}