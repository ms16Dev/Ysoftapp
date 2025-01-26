package com.msan.ysoftapp.feature.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msan.ysoftapp.domain.model.Assignment
import com.msan.ysoftapp.extention.toDate
import com.msan.ysoftapp.feature.home.usecase.GetAssignmentsUseCase
import com.msan.ysoftapp.feature.home.usecase.UpdateAssignmentUseCase
import com.msan.ysoftapp.extention.toFormattedYearMonthDateString
import com.msan.ysoftapp.feature.home.model.CalendarModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAssignmentsUseCase: GetAssignmentsUseCase,
    private val updateAssignmentUseCase: UpdateAssignmentUseCase,
    private val savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    private val _selectedDate = MutableStateFlow(Date())
    private val _dateFilter = savedStateHandle.getStateFlow(
        DATE_FILTER_KEY,
        Date().toFormattedYearMonthDateString()
    )
    private val _greeting = MutableStateFlow("")
    private val _userName = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _assignments = _dateFilter.flatMapLatest { selectedDate ->
        getAssignmentsUseCase.getAssignments(selectedDate.toDate())
    }

    val homeUiState = combine(
        _selectedDate,
        _assignments,
        _dateFilter,
        _greeting,
        _userName
    ) { selectedDate, assignments, dateFilter, greeting, userName ->
        HomeState(
            lastSelectedDate = dateFilter,
            assignments = assignments.sortedBy { it.startDate },
            greeting = greeting,
            userName = userName
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        HomeState(lastSelectedDate = Date().toFormattedYearMonthDateString())
    )

    fun updateSelectedDate(date: Date) {
        _selectedDate.value = date
    }

    init {
        getUserName()
        getGreeting()
    }

    private fun getUserName() {
        _userName.value = "Kathryn"
        // TODO: Get user name from DB
    }

    private fun getGreeting() {
        _greeting.value = "Greeting"
        // TODO: Get greeting by checking system time
    }

    fun selectDate(selectedDate: CalendarModel.DateModel) {
        savedStateHandle[DATE_FILTER_KEY] = selectedDate.date.toFormattedYearMonthDateString()
    }

    fun takeAssignment(assignment: Assignment) {
        viewModelScope.launch {
            updateAssignmentUseCase.updateAssignment(assignment)
        }

    }

    companion object {
        const val DATE_FILTER_KEY = "assignment_date_filter"
    }
}