package com.example.datamodule

import org.junit.Test

class TodoRepositoryTest {

    @Test
    fun `test returns correct number model sequence`() {
        val numRepo = TodoRepository()
        println(numRepo.getTodos())
        println(numRepo.getTodos())
    }

}