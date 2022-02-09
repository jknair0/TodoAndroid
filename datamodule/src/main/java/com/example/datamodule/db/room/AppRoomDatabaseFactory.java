package com.example.datamodule.db.room;

import android.content.Context;
import androidx.room.Room;

public class AppRoomDatabaseFactory {

    public static AppRoomDatabase create(Context context) {
        return Room.databaseBuilder(context, AppRoomDatabase.class, "database-name").build();
    }
}
