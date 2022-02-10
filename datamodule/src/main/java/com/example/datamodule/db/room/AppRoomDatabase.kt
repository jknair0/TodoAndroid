package com.example.datamodule.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.datamodule.db.room.models.SubTodoModel
import com.example.datamodule.db.room.models.TodoModel

@Database(entities = [TodoModel::class, SubTodoModel::class], version = 1)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}