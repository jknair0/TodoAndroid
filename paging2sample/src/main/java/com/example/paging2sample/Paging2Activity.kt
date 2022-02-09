package com.example.paging2sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.example.paging2sample.widget.TodoList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Paging2Activity : ComponentActivity() {

    private val viewModel: Paging2ViewModel by viewModels()

    @Inject
    lateinit var mainActivityLauncher: MainActivityLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Surface(modifier = Modifier.weight(1f)) {
                    TodoList(viewModel)
                }
                Button(onClick = {
                    mainActivityLauncher.launch(this@Paging2Activity)
                }) { Text(text = "Launch Main") }
            }
        }
        viewModel.init()
    }
}