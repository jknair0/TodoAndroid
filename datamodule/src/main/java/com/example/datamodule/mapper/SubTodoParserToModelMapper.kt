package com.example.datamodule.mapper

import com.example.datamodule.db.models.SubTodoModel
import com.example.datamodule.network.SubTodoParser
import javax.inject.Inject

class SubTodoParserToModelMapper @Inject constructor() : Mapper<SubTodoParser, SubTodoModel> {
    override fun map(from: SubTodoParser): SubTodoModel {
        return with(from) {
            SubTodoModel(
                parentId,
                subTodoId,
                title,
                completed
            )
        }
    }
}