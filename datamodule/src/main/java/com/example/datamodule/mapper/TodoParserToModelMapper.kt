package com.example.datamodule.mapper

import com.example.datamodule.db.models.SubTodoModel
import com.example.datamodule.db.models.TodoModel
import com.example.datamodule.network.SubTodoParser
import com.example.datamodule.network.TodoParser
import javax.inject.Inject

class TodoParserToModelMapper @Inject constructor(
    private val subTodoMapper: Mapper<SubTodoParser, SubTodoModel>
): Mapper<TodoParser, TodoModel> {
    override fun map(from: TodoParser): TodoModel {
        return with(from) {
            TodoModel(
                id,
                title,
                completed,
                subTodos.map(subTodoMapper::map)
            )
        }
    }
}