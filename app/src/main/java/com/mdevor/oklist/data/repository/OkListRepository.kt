package com.mdevor.oklist.data.repository

import com.mdevor.oklist.data.datasource.remote.TaskRemoteEntity
import kotlinx.coroutines.flow.Flow

interface OkListRepository {

    suspend fun getAllTasks(userEmail: String): Flow<List<TaskRemoteEntity>>

    suspend fun upsertTask(task: TaskRemoteEntity)

    suspend fun deleteTask(id: String)

    suspend fun getTaskById(id: String): TaskRemoteEntity?
}