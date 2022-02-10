package com.example.datamodule.di

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import com.example.datamodule.db.room.AppRoomDatabase
import com.example.datamodule.db.room.AppRoomDatabaseFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun providesAppRoomDatabase(@ApplicationContext context: Context?): AppRoomDatabase {
        return AppRoomDatabaseFactory.create(context)
    }

}