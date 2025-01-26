package com.msan.ysoftapp.feature.home.viewmodel

import com.msan.ysoftapp.domain.model.Assignment

data class HomeState (

    val greeting: String = "",
    val userName: String = "",
    val lastSelectedDate: String,
    val assignments: List<Assignment> = emptyList(),
)