package com.example.datamodule.db.realm

import android.os.Handler
import android.os.HandlerThread
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.asCoroutineDispatcher

@Suppress("unused")
val Dispatchers.Realm
    get() = RealmDispatcher

private val RealmDispatcher by lazy { realmThread() }

private fun realmThread(): CoroutineDispatcher {
    val handlerThread = HandlerThread("realm-thread")
    handlerThread.start()
    val handler = Handler(handlerThread.looper)
    return handler.asCoroutineDispatcher()
}