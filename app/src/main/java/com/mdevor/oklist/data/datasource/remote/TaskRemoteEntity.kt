package com.mdevor.oklist.data.datasource.remote

data class TaskRemoteEntity(
    val id: String = "",
    val title: String = "",
    val deadlineDate: String = "",
    val color: Long = 0L,
    val ownerEmail: String = "",
    val collaboratorEmail: String? = null,
    val subtaskList: List<SubtaskRemoteEntity> = emptyList(),
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "deadlineDate" to deadlineDate,
            "color" to color,
            "ownerEmail" to ownerEmail,
            "collaboratorEmail" to collaboratorEmail,
            "subtaskList" to subtaskList.map { it.toMap() }
        )
    }
}