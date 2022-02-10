package com.example.datamodule.db.room

import android.util.Log
import com.example.datamodule.db.DbSource
import com.example.datamodule.db.room.models.SubTodoModel
import com.example.datamodule.db.room.models.TodoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DbSourceImpl @Inject constructor(
    appRoomDatabase: AppRoomDatabase
) : DbSource {

    private val todoDao = appRoomDatabase.todoDao()

    override fun listTodo(): Flow<List<TodoModel>> {
        return todoDao.getAllTodoWithSubModels().map { todoWithSubModels ->
            printTodos(todoWithSubModels)
            todoWithSubModels.map { it.todos.copy(subTodoModel = it.subModels) }
        }
    }

    private fun <T> printTodos(todosWithSubModels: List<T>) {
        with(StringBuilder()) {
            for (todo in todosWithSubModels) {
                append(" - $todo \n")
            }
            println(toString())
        }
    }

    override suspend fun getTodoById(id: Int): TodoModel? = withContext(Dispatchers.IO) {
        try {
            todoDao.getTodoById(id)
        } catch (e: Exception) {
            Log.e("DbSourceImpl", "getTodoById failed ", e)
            null
        }
    }

    override suspend fun getSubTodoByParentId(parentId: Int): List<SubTodoModel> {
        return try {
            todoDao.getAllSubModel(parentId)
        } catch (e: Exception) {
            Log.e("DbSourceImpl", "getTodoById failed ", e)
            emptyList()
        }
    }

    override suspend fun insertTodo(todoModels: List<TodoModel>) {
        todoDao.insertTodoAndSubs(todoModels)
    }

    override suspend fun getSubTodoById(id: Int): SubTodoModel? {
        return try {
            todoDao.getSubTodoById(id)
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun insertSubTodo(flatMap: List<SubTodoModel>) {
        todoDao.insertSubTodo(flatMap)
    }

}