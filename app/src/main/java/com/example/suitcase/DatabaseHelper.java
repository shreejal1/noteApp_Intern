package com.example.suitcase;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Note.class, exportSchema = false, version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "note_db";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getDB(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, DatabaseHelper.class, DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract NoteDao noteDao();

}
