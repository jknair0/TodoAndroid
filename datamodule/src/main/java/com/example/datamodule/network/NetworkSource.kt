package com.example.datamodule.network


interface NetworkSource {

    suspend fun getTodosByPage(page: Int, pageSize: Int): List<TodoParser>

    suspend fun getTodos(): List<TodoParser>

}