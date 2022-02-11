package com.example.datamodule.db

import com.example.datamodule.db.room.models.SubTodoModel
import com.example.datamodule.db.room.models.TodoModel
import kotlinx.coroutines.flow.Flow

interface DbSource {

    suspend fun getTodoListByPage(page: Int): List<TodoModel>

    fun listTodo(): Flow<List<TodoModel>>

    suspend fun getTodoById(id: Int): TodoModel?

    suspend fun getSubTodoById(id: Int): SubTodoModel?

    suspend fun getSubTodoByParentId(parentId: Int): List<SubTodoModel>

    suspend fun insertTodo(todoModels: List<TodoModel>)

    suspend fun insertSubTodo(flatMap: List<SubTodoModel>)

}