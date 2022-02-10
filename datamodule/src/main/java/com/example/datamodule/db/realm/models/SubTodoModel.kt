package com.example.datamodule.db.realm.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SubTodoModel(
    @PrimaryKey
    var subTodoId: Int,
    var title: String,
    var completed: Boolean
) : RealmObject() {

    constructor() : this(0, "", false)
}
