package com.example.datamodule.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkSourceImpl @Inject constructor() : NetworkSource {

    private val listOfTodos = (1..10).map { getTodoParser(it) }

    override suspend fun getTodosByPage(page: Int, pageSize: Int): List<TodoParser> {
        Log.i(NetworkSourceImpl::class.simpleName, "getTodosByPage: $page $pageSize")
        return (1..pageSize).map { i ->
            val id = (pageSize * page) - (pageSize - i)
            TodoParser(
                id,
                "todo $id page $page",
                false,
                emptyList(),
                page
            )
        }
    }

    override suspend fun getTodos(): List<TodoParser> = withContext(Dispatchers.IO) {
        delay(700)
        listOfTodos
    }

    companion object {
        private fun getTodoParser(id: Int): TodoParser {
            return TodoParser(
                id,
                "todo $id",
                false,
                (1..id).map {
                    val subTodoId = (id * 10) + it
                    SubTodoParser(
                        id,
                        subTodoId,
                        "subtodo $subTodoId",
                        false
                    )
                },
                1
            )
        }
    }
}