package com.example.paging2sample.widget

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.datamodule.SubTodoEntity
import com.example.datamodule.TodoEntity
import com.example.paging2sample.Paging2ViewModel

@Composable
fun TodoList(viewModel: Paging2ViewModel) {
    val todoList: List<TodoEntity> by viewModel.listFlow.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.init()
    }

    LazyColumn {
        items(todoList.size, { index -> todoList[index].id }) {
            Column {
                val todo = todoList[it]
                TodoItemWidget(todo) { newValue, ofTodo ->
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
    todo: TodoEntity,
    onCheckChanged: (updatedValue: Boolean, todo: TodoEntity) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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
    TodoItemWidget(
        todo = TodoEntity(1, "Todo Item", false, emptyList()),
        onCheckChanged = { _, _ -> }
    )
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
