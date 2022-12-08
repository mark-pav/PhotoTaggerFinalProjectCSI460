package com.example.finalprojectbymarkpavlyuk;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Photos.class, Tags.class}, version = 1)
@TypeConverters({ImageBitmapString.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract PhotoDao photoDao();

    public static final String DATABASE_NAME = "AppDB";

    public static AppDatabase instance;

    public static AppDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context,AppDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }

        return instance;
    }
}
