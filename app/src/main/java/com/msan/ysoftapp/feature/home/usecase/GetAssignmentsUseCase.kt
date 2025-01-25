package com.msan.ysoftapp.feature.home.usecase

import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.domain.repository.AssignmentRepository
import com.msan.ysoftapp.extention.toMillis
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject


class GetAssignmentsUseCase @Inject constructor(
    private val repository: AssignmentRepository
) {

    fun getAssignments(date: String? = null): Flow<List<Assignment>> {
        return if (date != null) {
            repository.getAssignmentsForDate(Date().toMillis(date))
        } else repository.getAllAssignments()    }
}