package com.example.datamodule.db.room.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class TodoModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var completed: Boolean,
    @Ignore
    var subTodoModel: List<SubTodoModel>,
    var page: Int
) {

    constructor() : this(0, "", false, emptyList(), 0)

}