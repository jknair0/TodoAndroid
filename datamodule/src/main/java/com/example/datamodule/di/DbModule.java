package com.example.datamodule.di;

import android.content.Context;
import com.example.datamodule.db.room.AppRoomDatabase;
import com.example.datamodule.db.room.AppRoomDatabaseFactory;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class DbModule {

    @Provides
    @Singleton
    public AppRoomDatabase providesAppRoomDatabase(@ApplicationContext Context context) {
        return AppRoomDatabaseFactory.create(context);
    }
}
