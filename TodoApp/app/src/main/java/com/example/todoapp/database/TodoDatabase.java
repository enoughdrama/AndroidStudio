package com.example.todoapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todoapp.model.TodoItem;

@Database(entities = {TodoItem.class}, version = 1, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {
    
    private static TodoDatabase instance;
    
    public abstract TodoDao todoDao();
    
    public static synchronized TodoDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TodoDatabase.class,
                    "todo_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}