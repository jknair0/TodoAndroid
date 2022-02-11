package com.example.datamodule

data class TodoEntity(
    var id: Int,
    var title: String,
    var completed: Boolean,
    var subTodos: List<SubTodoEntity>,
    var page: Int
)