package com.example.datamodule.network

data class TodoParser(
    var id: Int,
    var title: String,
    var completed: Boolean,
    var subTodos: List<SubTodoParser>
)

