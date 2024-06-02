package com.mdevor.oklist.data.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.PersistentCacheSettings
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.snapshots
import com.mdevor.oklist.data.datasource.remote.TaskRemoteEntity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class OkListRepositoryImpl @Inject constructor(
    private val remoteDataSource: FirebaseFirestore,
) : OkListRepository {

    private val taskCollection = remoteDataSource.collection("tasks")

    override suspend fun getAllTasks(userEmail: String): Flow<List<TaskRemoteEntity>> {
        val ownerQuery = taskCollection.whereEqualTo("ownerEmail", userEmail)
        val collaboratorQuery = taskCollection.whereEqualTo("collaboratorEmail", userEmail)

        val taskFlow = combine(
            ownerQuery.snapshots().map { snapshot ->
                snapshot.toObjects(TaskRemoteEntity::class.java)
            },
            collaboratorQuery.snapshots().map { snapshot ->
                snapshot.toObjects(TaskRemoteEntity::class.java)
            }
        ) { ownerTasks, collaboratorTasks ->
            val allTasks = mutableListOf<TaskRemoteEntity>()
            allTasks.addAll(ownerTasks)
            allTasks.addAll(collaboratorTasks)
            allTasks.distinctBy { it.id }
        }
        return taskFlow
    }

    /**
     * Upsert a task in Firestore.
     * If the task doesn't exist, it will be added. If it does exist, it will be updated.
     */
    override suspend fun upsertTask(task: TaskRemoteEntity) {
        val taskId = task.id
        val taskDocRef = taskCollection.document(taskId)

        remoteDataSource.runTransaction { transaction ->
            val snapshot = transaction.get(taskDocRef)
            if (snapshot.exists()) {
                transaction.update(taskDocRef, task.toMap()) // Update the existing task
            } else {
                transaction.set(taskDocRef, task) // Add the task if it doesn't exist
            }
        }.addOnSuccessListener {
            Log.d("Firestore", "Task upserted!")
        }.addOnFailureListener { e ->
            Log.d("Firestore", "Error upserting task", e)
        }
    }

    override suspend fun getTaskById(id: String): TaskRemoteEntity? {
        val taskDoc = taskCollection.document(id).get().await()
        return taskDoc.toObject(TaskRemoteEntity::class.java)
    }


    override suspend fun deleteTask(id: String) {
        taskCollection.document(id).delete().addOnSuccessListener {
            Log.d("Firestore", "Task deleted!")
        }.addOnFailureListener { e ->
            Log.d("Firestore", "Error deleting task", e)
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface OkListRepositoryModule {
    @Binds
    fun bindRepository(repository: OkListRepositoryImpl): OkListRepository
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore.apply {
            firestoreSettings = firestoreSettings {
                setLocalCacheSettings(PersistentCacheSettings.newBuilder().build())
            }
        }
    }
}