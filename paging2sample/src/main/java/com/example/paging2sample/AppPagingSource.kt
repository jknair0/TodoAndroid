package com.example.paging2sample

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.datamodule.TodoEntity
import com.example.datamodule.TodoRepository
import kotlinx.coroutines.delay
import java.lang.RuntimeException
import javax.inject.Inject

class AppPagingSource @Inject constructor(
    private val repo: TodoRepository
) : PagingSource<Int, TodoEntity>() {

    override fun getRefreshKey(state: PagingState<Int, TodoEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TodoEntity> {
//        Log.d("AppPagingSource", "$params ${params.key} ${params.loadSize}")
//        try {
//            val page = requireNotNull(params.key)
//            val currentData = repo.getTodos()
//            delay(700)
//            if (currentData.isEmpty()) {
//                return LoadResult.Invalid()
//            }
//            return LoadResult.Page(currentData,  null, page + 1)
//        } catch (e: Exception) {
            return LoadResult.Error(RuntimeException())
//        }
    }
}