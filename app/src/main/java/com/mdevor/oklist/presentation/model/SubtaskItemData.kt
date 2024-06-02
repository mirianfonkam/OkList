package com.mdevor.oklist.presentation.model

data class SubtaskItemData(
    val uuid: String,
    val description: String,
    val isCompleted: Boolean = false,
)