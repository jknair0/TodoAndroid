package com.example.paging2sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.datamodule.SubTodoEntity
import com.example.datamodule.TodoEntity
import com.example.datamodule.TodoRepository
import com.example.datamodule.db.DbSource
import com.example.datamodule.db.room.models.TodoModel
import com.example.datamodule.mapper.Mapper
import com.example.datamodule.network.NetworkSource
import com.example.datamodule.network.TodoParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.SharingStarted.Companion
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.replay
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Paging2ViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val networkSource: NetworkSource,
    private val dbSource: DbSource,
    private val toModelMapper: Mapper<TodoParser, TodoModel>
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

    @OptIn(ExperimentalPagingApi::class)
    val pagingFlow: Flow<PagingData<TodoEntity>> = Pager(
        PagingConfig(initialLoadSize = 9, pageSize = 9),
        pagingSourceFactory = { AppPagingSource(todoRepository, networkSource, dbSource, toModelMapper) }
    ).flow.cachedIn(viewModelScope)

    val listFlow: Flow<List<TodoEntity>> = todoRepository.getTodos()
        .flowOn(Dispatchers.Main)
        .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

}