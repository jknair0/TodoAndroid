package com.example.paging2sample

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.datamodule.TodoEntity
import com.example.datamodule.db.DbSource
import com.example.datamodule.mapper.Mapper
import com.example.datamodule.network.NetworkSource
import com.example.datamodule.network.TodoParser
import javax.inject.Inject

/**
 * this doesnt make sense we we are having a paging source
 */
@OptIn(ExperimentalPagingApi::class)
class AppRemoteMediator @Inject constructor(
    private val networkSource: NetworkSource,
    private val dbSource: DbSource,
    private val toModelMapper: Mapper<TodoParser, com.example.datamodule.db.room.models.TodoModel>
) : RemoteMediator<Int, TodoEntity>() {

    val TAG = AppRemoteMediator::class.simpleName

    override suspend fun load(loadType: LoadType, state: PagingState<Int, TodoEntity>): MediatorResult {
        Log.i(TAG, "requested $loadType")
        val page = when (loadType) {
            REFRESH -> 1
            PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            APPEND -> state.lastItemOrNull()?.page ?: return MediatorResult.Success(endOfPaginationReached = true)
        }
        val todos = networkSource.getTodosByPage(page, state.config.pageSize)
        dbSource.insertTodo(todos.map { toModelMapper.map(it) })
        return MediatorResult.Success(endOfPaginationReached = false)
    }
}