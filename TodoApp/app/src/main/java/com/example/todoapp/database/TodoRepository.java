package com.example.todoapp.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.todoapp.model.TodoItem;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TodoRepository {
    
    private TodoDao todoDao;
    private LiveData<List<TodoItem>> allTodos;
    private LiveData<List<TodoItem>> activeTodos;
    private LiveData<List<TodoItem>> completedTodos;
    private ExecutorService executorService;
    
    public TodoRepository(Application application) {
        TodoDatabase database = TodoDatabase.getInstance(application);
        todoDao = database.todoDao();
        allTodos = todoDao.getAllTodos();
        activeTodos = todoDao.getActiveTodos();
        completedTodos = todoDao.getCompletedTodos();
        executorService = Executors.newSingleThreadExecutor();
    }
    
    public void insert(TodoItem todoItem) {
        executorService.execute(() -> todoDao.insert(todoItem));
    }
    
    public void update(TodoItem todoItem) {
        executorService.execute(() -> todoDao.update(todoItem));
    }
    
    public void delete(TodoItem todoItem) {
        executorService.execute(() -> todoDao.delete(todoItem));
    }
    
    public void deleteAll() {
        executorService.execute(() -> todoDao.deleteAll());
    }
    
    public LiveData<List<TodoItem>> getAllTodos() {
        return allTodos;
    }
    
    public LiveData<List<TodoItem>> getActiveTodos() {
        return activeTodos;
    }
    
    public LiveData<List<TodoItem>> getCompletedTodos() {
        return completedTodos;
    }
}