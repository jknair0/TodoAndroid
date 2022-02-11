package com.example.datamodule.db.realm

import android.util.Log
import com.example.datamodule.db.realm.models.SubTodoModel
import com.example.datamodule.db.realm.models.TodoModel
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Suppress("EXPERIMENTAL_API_USAGE")
class TodoRealmDao @Inject constructor() {

    init {
        runBlocking {
            transaction {
                deleteAll()
            }
        }
    }

    fun getAll(): Flow<List<TodoModel>> {
        return callbackFlow {
            val realm = Realm.getDefaultInstance()
            val query: RealmResults<TodoModel> = realm.where(TodoModel::class.java).findAll()
            val todoChangeListener = RealmChangeListener<RealmResults<TodoModel>> { results ->
                trySend(realm.copyFromRealm(results))
            }
            query.addChangeListener(todoChangeListener)

            awaitClose {
                query.removeChangeListener(todoChangeListener)
                close()
            }
        }.flowOn(Dispatchers.Realm)
    }

    suspend fun getByIdPage(page: Int): List<TodoModel> {
        return realm {
            val realm = Realm.getDefaultInstance()
            realm.where(TodoModel::class.java)
                .equalTo("page", page)
                .findAll().map { copyFromRealm(it) }
        }
    }

    suspend fun insert(todoModel: List<TodoModel>) {
        return transaction {
            insertOrUpdate(todoModel)
        }
    }

    suspend fun insertSubTodo(todoModel: List<SubTodoModel>) {
        return transaction {
            insertOrUpdate(todoModel)
        }
    }

    suspend fun getTodoById(id: Int): TodoModel? {
        return realm {
            where(TodoModel::class.java)
                .equalTo("id", id)
                .findFirst()?.let { copyFromRealm(it) }
        }
    }

    suspend fun getSubTodoById(id: Int): SubTodoModel? {
        return realm {
            where(SubTodoModel::class.java)
                .equalTo("subTodoId", id)
                .findFirst()?.let { copyFromRealm(it) }
        }
    }

    private suspend fun <T> realm(block: Realm.() -> T): T = withContext(Dispatchers.Realm) {
        Realm.getDefaultInstance().use(block)
    }

    private suspend fun transaction(block: Realm.() -> Unit) {
        withContext(Dispatchers.Realm) {
            Realm.getDefaultInstance().use {
                it.beginTransaction()
                it.block()
                it.commitTransaction()
                Log.i("Realm", "insert succeeded")
            }
        }
    }

}
