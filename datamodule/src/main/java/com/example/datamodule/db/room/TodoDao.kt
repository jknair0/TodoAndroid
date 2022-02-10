package com.example.datamodule.db.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.datamodule.db.room.models.SubTodoModel
import com.example.datamodule.db.room.models.TodoModel
import com.example.datamodule.db.room.models.TodoWithSubModels
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TodoDao {

    @Query("SELECT * FROM todomodel JOIN subtodomodel on todomodel.id=subtodomodel.parentId GROUP BY parentId")
    abstract fun getAllTodoWithSubModels(): Flow<List<TodoWithSubModels>>

    @Query("SELECT * FROM todomodel")
    abstract fun getAll(): Flow<List<TodoModel>>

    @Query("SELECT * FROM subtodomodel where parentId=:parentId")
    abstract suspend fun getAllSubModel(parentId: Int): List<SubTodoModel>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(todoModel: List<TodoModel>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSubTodo(todoModel: List<SubTodoModel>)

    @Query("SELECT * FROM todomodel where id = :id")
    abstract suspend fun getTodoById(id: Int): TodoModel

    @Query("SELECT * FROM subtodomodel where subTodoId = :id")
    abstract suspend fun getSubTodoById(id: Int): SubTodoModel

    @Transaction
    open suspend fun insertTodoAndSubs(todos: List<TodoModel>) {
        insert(todos)
        insertSubTodo(todos.flatMap { it.subTodoModel })
    }

}
