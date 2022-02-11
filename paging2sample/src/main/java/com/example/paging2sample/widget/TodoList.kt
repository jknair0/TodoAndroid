package com.example.paging2sample.widget

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.datamodule.SubTodoEntity
import com.example.datamodule.TodoEntity
import com.example.paging2sample.Paging2ViewModel
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun TodoList(viewModel: Paging2ViewModel) {
//    val todoList: List<TodoEntity> by viewModel.listFlow.collectAsState(initial = emptyList())
    val todoList: LazyPagingItems<TodoEntity> = viewModel.pagingFlow.collectAsLazyPagingItems()

//    LaunchedEffect(Unit) {
//        viewModel.init()
//    }

    LazyColumn {
        Log.i("TodoList", "TodoList: new list received "+todoList.itemCount)
        items(todoList, { todoEntity -> todoEntity.id }) { todoEntity ->
            Column {
                val todo = requireNotNull(todoEntity)
                TodoItemWidget(viewModel, todo) { newValue, ofTodo ->
                    viewModel.updateTodo(newValue, ofTodo)
                }
                val subTodos = todo.subTodos
                for (subTodo in subTodos) {
                    SubTodoItemWidget(subTodo, onCheckChanged = { newValue, ofSubTodo ->
                        viewModel.updateSubTodo(newValue, ofSubTodo)
                    })
                }
            }
        }
//        items(todoList.size, { index -> todoList[index].id }) {
//            Column {
//                val todo = todoList[it]
//                TodoItemWidget(todo) { newValue, ofTodo ->
//                    viewModel.updateTodo(newValue, ofTodo)
//                }
//                val subTodos = todo.subTodos
//                for (subTodo in subTodos) {
//                    SubTodoItemWidget(subTodo, onCheckChanged = { newValue, ofSubTodo ->
//                        viewModel.updateSubTodo(newValue, ofSubTodo)
//                    })
//                }
//            }
//        }
        /*data.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        LoadingView(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingItem()
                    }
                }
            }
        }*/
    }
}

@Composable
private fun TodoItemWidget(
    viewModel: Paging2ViewModel,
    initialValue: TodoEntity,
    onCheckChanged: (updatedValue: Boolean, todo: TodoEntity) -> Unit
) {
    println("TodoItemWidget rendered "+initialValue.id)
    val todoList: State<List<TodoEntity>> = viewModel.listFlow.collectAsState(initial = listOf(initialValue))
    val todo = remember {
        derivedStateOf {
            val find = todoList.value.find { it.id == initialValue.id }
            println("it was null for "+ initialValue.id)
            find ?: initialValue
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Checkbox(
            modifier = Modifier.align(Alignment.CenterVertically),
            checked = todo.value.completed,
            onCheckedChange = {
                onCheckChanged(!todo.value.completed, todo.value)
            }
        )
        Text(
            text = todo.value.title,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun SubTodoItemWidget(
    todo: SubTodoEntity,
    onCheckChanged: (updatedValue: Boolean, todo: SubTodoEntity) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(start = 32.dp)
    ) {
        Checkbox(
            modifier = Modifier.align(Alignment.CenterVertically),
            checked = todo.completed,
            onCheckedChange = {
                onCheckChanged(!todo.completed, todo)
            }
        )
        Text(
            text = todo.title,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
@Preview
private fun TodoItemWidgetPreview() {
//    TodoItemWidget(
//        viewModel = viewModel,
//        todo = TodoEntity(1, "Todo Item", false, emptyList(), 1)
//    ) { _, _ -> }
}

@Composable
fun LoadingView(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier)
}

@Composable
fun LoadingItem() {
    Row(
        modifier = Modifier
            .padding(PaddingValues(16.dp))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Loading...")
    }
}
