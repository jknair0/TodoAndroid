package com.example.paging2sample

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.datamodule.TodoEntity
import com.example.datamodule.TodoRepository
import com.example.datamodule.db.DbSource
import com.example.datamodule.db.room.models.TodoModel
import com.example.datamodule.mapper.Mapper
import com.example.datamodule.network.NetworkSource
import com.example.datamodule.network.TodoParser
import javax.inject.Inject

class AppPagingSource @Inject constructor(
    private val repo: TodoRepository,
    private val networkSource: NetworkSource,
    private val dbSource: DbSource,
    private val toModelMapper: Mapper<TodoParser, TodoModel>
) : PagingSource<Int, TodoEntity>() {

    override fun getRefreshKey(state: PagingState<Int, TodoEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TodoEntity> {
        return try {
            val page = params.key ?: 1

            val todos = networkSource.getTodosByPage(page, params.loadSize)
            dbSource.insertTodo(todos.map { toModelMapper.map(it) })
            val currentData = repo.getTodoListByPage(page)
            val nextPage = (page + 1).takeIf { currentData.isNotEmpty() }

            LoadResult.Page(currentData, null, nextPage)
        } catch (e: Exception) {
            Log.i("AppPagingSource", "error", e)
            LoadResult.Error(e)
        }
    }
}