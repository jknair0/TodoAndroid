package com.example.datamodule.mapper

import com.example.datamodule.SubTodoEntity
import com.example.datamodule.db.room.models.SubTodoModel
import javax.inject.Inject

class SubModelToEntityMapper @Inject constructor() : Mapper<SubTodoModel, SubTodoEntity> {
    override fun map(from: SubTodoModel): SubTodoEntity {
        return with(from) {
            SubTodoEntity(
                parentId,
                subTodoId,
                title,
                completed
            )
        }
    }

}