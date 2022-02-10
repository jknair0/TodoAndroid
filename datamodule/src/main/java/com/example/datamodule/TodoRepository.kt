package com.example.datamodule

import com.example.datamodule.db.DbSource
import com.example.datamodule.db.room.models.TodoModel
import com.example.datamodule.mapper.Mapper
import com.example.datamodule.network.NetworkSource
import com.example.datamodule.network.TodoParser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val dbSource: DbSource,
    private val networkSource: NetworkSource,
    private val todoModelMapper: Mapper<TodoModel, TodoEntity>,
    private val todoParserToModelMapper: Mapper<TodoParser, TodoModel>
) {

    fun getTodos(): Flow<List<TodoEntity>> {
        return dbSource.listTodo().map { todos ->
            todos.map(todoModelMapper::map)
        }
    }

    suspend fun fetchTodos() {
        val parsers: List<TodoParser> = networkSource.getTodos()
        val todoModels = parsers.map(todoParserToModelMapper::map)
        dbSource.insertTodo(todoModels)
        dbSource.insertSubTodo(todoModels.flatMap { it.subTodoModel })
    }

    suspend fun updateTodoCheck(newValue: Boolean, ofTodo: TodoEntity) {
        val existingTodo = dbSource.getTodoById(ofTodo.id) ?: return
        val subTodoList = dbSource.getSubTodoByParentId(ofTodo.id).map { it.copy(completed = newValue) }
        existingTodo.completed = newValue
        existingTodo.subTodoModel = subTodoList
        dbSource.insertTodo(listOf(existingTodo))
    }

    suspend fun updateSubTodoCheck(newValue: Boolean, ofSubTodo: SubTodoEntity) {
        val existingTodo = dbSource.getSubTodoById(ofSubTodo.subTodoId) ?: return
        dbSource.insertSubTodo(listOf(existingTodo.copy(completed = newValue)))
    }

}