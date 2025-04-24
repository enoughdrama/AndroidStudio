package com.example.todoapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoapp.model.TodoItem;

import java.util.List;

@Dao
public interface TodoDao {
    
    @Insert
    void insert(TodoItem todoItem);
    
    @Update
    void update(TodoItem todoItem);
    
    @Delete
    void delete(TodoItem todoItem);
    
    @Query("DELETE FROM todo_items")
    void deleteAll();
    
    @Query("SELECT * FROM todo_items ORDER BY createdAt DESC")
    LiveData<List<TodoItem>> getAllTodos();
    
    @Query("SELECT * FROM todo_items WHERE completed = 0 ORDER BY createdAt DESC")
    LiveData<List<TodoItem>> getActiveTodos();
    
    @Query("SELECT * FROM todo_items WHERE completed = 1 ORDER BY createdAt DESC")
    LiveData<List<TodoItem>> getCompletedTodos();
}