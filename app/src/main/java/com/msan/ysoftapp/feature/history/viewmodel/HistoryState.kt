package com.msan.ysoftapp.feature.history.viewmodel

import com.msan.ysoftapp.domain.model.Assignment

data class HistoryState(
    val assignments: List<Assignment> = emptyList()
)