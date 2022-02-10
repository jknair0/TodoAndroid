package com.example.datamodule.db.room.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = TodoModel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("parentId"),
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class SubTodoModel(
    val parentId: Int,
    @PrimaryKey
    val subTodoId: Int,
    val title: String,
    val completed: Boolean
) {

    constructor() : this(0, 0, "", false)
}
