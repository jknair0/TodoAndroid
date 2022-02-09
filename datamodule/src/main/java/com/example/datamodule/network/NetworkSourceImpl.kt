package com.example.datamodule.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkSourceImpl @Inject constructor() : NetworkSource {

    private val listOfTodos = (1..10).map { getTodoParser(it) }

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
                }
            )
        }
    }
}