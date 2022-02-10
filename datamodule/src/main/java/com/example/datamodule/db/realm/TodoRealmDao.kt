package com.example.datamodule.db.realm

import android.util.Log
import com.example.datamodule.db.realm.models.SubTodoModel
import com.example.datamodule.db.realm.models.TodoModel
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRealmDao @Inject constructor(
    private val realm: Realm,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    private val results: RealmResults<TodoModel> by lazy { realm.where(TodoModel::class.java).findAll() }

    private val mutableFlow: Flow<List<TodoModel>> = callbackFlow {
        results.addChangeListener(RealmChangeListener<RealmResults<TodoModel>> {
            Log.e("TodoRealmDao", "getAll: " + Thread.currentThread().name)
            trySend(realm.copyFromRealm(it))
        })

        awaitClose {
            Log.e("TodoRealmDao", "getAll closed ")
        }
    }.flowOn(coroutineDispatcher)

    fun getAll(): Flow<List<TodoModel>> {
        return mutableFlow
    }

    suspend fun insert(todoModel: List<TodoModel>) {
        withContext(coroutineDispatcher) {
            realm.executeTransaction {
                realm.insertOrUpdate(todoModel)
            }
            Log.e("TodoRealmDao", "insertOrUpdate todoModel $todoModel ${todoModel.joinToString { "${it.completed}" }}")
        }
    }

    suspend fun insertSubTodo(todoModel: List<SubTodoModel>) {
        withContext(coroutineDispatcher) {
            realm.executeTransaction {
                realm.insertOrUpdate(todoModel)
            }
            Log.e("TodoRealmDao", "insertOrUpdate SubTodoModel $todoModel ${todoModel.joinToString { "${it.completed}" }}")
        }
    }

    suspend fun getTodoById(id: Int): TodoModel? {
        return withContext(coroutineDispatcher) {
            realm.where(TodoModel::class.java)
                .equalTo("id", id)
                .findFirst()?.let { realm.copyFromRealm(it) }
        }
    }

    suspend fun getSubTodoById(id: Int): SubTodoModel? {
        return withContext(coroutineDispatcher) {
            realm.where(SubTodoModel::class.java)
                .equalTo("subTodoId", id)
                .findFirst()?.let { realm.copyFromRealm(it) }
        }
    }

}
