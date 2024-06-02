package com.mdevor.oklist.presentation.screens.profile

data class ProfileUiState(
    val displayName: String = "",
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val pendingTasks: Int = 0,
    val dateToday: String = "",
)