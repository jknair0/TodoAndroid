package com.example.datamodule.db.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class TodoModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var completed: Boolean,
    @Ignore
    var subTodoModel: List<SubTodoModel>
) {

    constructor(): this(0, "", false, emptyList())

}