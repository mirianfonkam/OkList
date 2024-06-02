package com.mdevor.oklist.data.datasource.remote


/**
 * Represents a subtask in the remote database.
 * Setters needed to be renamed to avoid conflicts with the getter.
 * Reference:
 * [Boolean fields that start with "is" in Firebase Firestore](https://medium.com/@eeddeellee/boolean-fields-that-start-with-is-in-firebase-firestore-49afb65e3639)
 */
data class SubtaskRemoteEntity(
    val id: String = "",
    val description: String = "",
    val completed: Boolean = false,
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "description" to description,
            "completed" to completed,
        )
    }
}