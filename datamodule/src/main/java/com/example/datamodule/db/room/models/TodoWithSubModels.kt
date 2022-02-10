package com.example.datamodule.db.room.models

import androidx.room.Embedded
import androidx.room.Relation

class TodoWithSubModels(
    @Embedded
    var todos: TodoModel,
    @Relation(parentColumn = "id", entityColumn = "parentId", entity = SubTodoModel::class)
    var subModels: List<SubTodoModel>
) {
    override fun toString(): String {
        return "TodoWithSubModels(todos=$todos, subModels=$subModels)"
    }
}