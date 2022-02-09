package com.example.paging2sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datamodule.SubTodoEntity
import com.example.datamodule.TodoEntity
import com.example.datamodule.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Paging2ViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    fun init() {
        viewModelScope.launch {
            todoRepository.fetchTodos()
        }
    }

    fun updateTodo(newValue: Boolean, ofTodo: TodoEntity) {
        viewModelScope.launch {
            todoRepository.updateTodoCheck(newValue, ofTodo)
        }
    }

    fun updateSubTodo(newValue: Boolean, ofSubTodo: SubTodoEntity) {
        viewModelScope.launch {
            todoRepository.updateSubTodoCheck(newValue, ofSubTodo)
        }
    }

    val listFlow: Flow<List<TodoEntity>> = todoRepository.getTodos().flowOn(Dispatchers.Main)

}