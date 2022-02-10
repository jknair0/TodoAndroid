package com.example.datamodule.di;

import com.example.datamodule.SubTodoEntity;
import com.example.datamodule.TodoEntity;
import com.example.datamodule.db.room.models.SubTodoModel;
import com.example.datamodule.db.room.models.TodoModel;
import com.example.datamodule.mapper.Mapper;
import com.example.datamodule.mapper.SubModelToEntityMapper;
import com.example.datamodule.mapper.SubTodoParserToModelMapper;
import com.example.datamodule.mapper.TodoModelToEntityMapper;
import com.example.datamodule.mapper.TodoParserToModelMapper;
import com.example.datamodule.network.SubTodoParser;
import com.example.datamodule.network.TodoParser;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
interface MapperModule {

    @Binds
    Mapper<TodoModel, TodoEntity> bindsTodoModelToEntityMapper(TodoModelToEntityMapper todoModelToEntityMapper);

    @Binds
    Mapper<SubTodoModel, SubTodoEntity> bindsSubModelToEntityMapper(SubModelToEntityMapper subModelToEntityMapper);

    @Binds
    Mapper<TodoParser, TodoModel> bindsTodoParserToModelMapper(TodoParserToModelMapper todoParserToModelMapper);

    @Binds
    Mapper<SubTodoParser, SubTodoModel> bindsSubTodoParserToModelMapper(SubTodoParserToModelMapper subTodoParserToModelMapper);
}