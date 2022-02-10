package com.example.datamodule.db.realm.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TodoModel(
    @PrimaryKey
    var id: Int,
    var title: String,
    var completed: Boolean,
    var subTodoModel: RealmList<SubTodoModel>
) : RealmObject() {

    constructor() : this(0, "", false, RealmList())

}