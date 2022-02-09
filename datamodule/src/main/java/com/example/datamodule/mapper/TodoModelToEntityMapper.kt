package com.example.datamodule.mapper

import com.example.datamodule.SubTodoEntity
import com.example.datamodule.TodoEntity
import com.example.datamodule.db.models.SubTodoModel
import com.example.datamodule.db.models.TodoModel
import javax.inject.Inject

class TodoModelToEntityMapper @Inject constructor(
    private val subTodoMapper: Mapper<SubTodoModel, SubTodoEntity>
): Mapper<TodoModel, TodoEntity> {
    override fun map(from: TodoModel): TodoEntity {
        return with(from) {
            TodoEntity(
                id,
                title,
                completed,
                subTodoModel.map(subTodoMapper::map)
            )
        }
    }

}