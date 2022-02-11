package com.example.datamodule.db.realm

import com.example.datamodule.db.DbSource
import com.example.datamodule.db.room.models.SubTodoModel
import com.example.datamodule.db.room.models.TodoModel
import io.realm.RealmList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.datamodule.db.realm.models.SubTodoModel as RealmSubTodoModel
import com.example.datamodule.db.realm.models.TodoModel as RealmTodoModel

class RealmDbSourceImpl @Inject constructor(
    private val realmDao: TodoRealmDao
) : DbSource {

    override suspend fun getTodoListByPage(page: Int): List<TodoModel> {
        return realmDao.getByIdPage(page).map {
            it.toRoomTodoModel()
        }
    }

    override fun listTodo(): Flow<List<TodoModel>> {
        return realmDao.getAll().map {
            it.map { realmTodoModel -> realmTodoModel.toRoomTodoModel() }
        }
    }

    override suspend fun getTodoById(id: Int): TodoModel? {
        return realmDao.getTodoById(id)?.toRoomTodoModel()
    }

    override suspend fun getSubTodoById(id: Int): SubTodoModel? {
        return realmDao.getSubTodoById(id)?.toRoomSubModel()
    }

    override suspend fun getSubTodoByParentId(parentId: Int): List<SubTodoModel> {
        return realmDao.getTodoById(parentId)?.subTodoModel?.map { it.toRoomSubModel() } ?: emptyList()
    }

    override suspend fun insertTodo(todoModels: List<TodoModel>) {
        return realmDao.insert(todoModels.map { it.toRoomTodoModel() })
    }

    override suspend fun insertSubTodo(flatMap: List<SubTodoModel>) {
        return realmDao.insertSubTodo(flatMap.map { it.toRealmSubTodoModel() })
    }

}

private fun TodoModel.toRoomTodoModel(): RealmTodoModel {
    return RealmTodoModel(
        id,
        title,
        completed,
        RealmList(* subTodoModel.map { it.toRealmSubTodoModel() }.toTypedArray()),
        page
    )
}

private fun SubTodoModel.toRealmSubTodoModel(): RealmSubTodoModel {
    return RealmSubTodoModel(
        subTodoId,
        title,
        completed
    )
}

private fun RealmTodoModel.toRoomTodoModel(): TodoModel {
    return TodoModel(
        id,
        title,
        completed,
        subTodoModel.map { it.toRoomSubModel() },
        page
    )
}

private fun RealmSubTodoModel.toRoomSubModel(): SubTodoModel {
    return SubTodoModel(
        0,
        subTodoId,
        title,
        completed
    )
}