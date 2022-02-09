package com.example.datamodule

class SubTodoEntity(
    val parentId: Int,
    val subTodoId: Int,
    val title: String,
    val completed: Boolean
)