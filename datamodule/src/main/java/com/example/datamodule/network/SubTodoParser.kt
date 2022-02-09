package com.example.datamodule.network

data class SubTodoParser(
    val parentId: Int,
    val subTodoId: Int,
    val title: String,
    val completed: Boolean
)