package com.example.datamodule.network


interface NetworkSource {

    suspend fun getTodos(): List<TodoParser>

}