package com.example.todoapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_items")
public class TodoItem {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String title;
    private String description;
    private boolean completed;
    private long createdAt;
    
    public TodoItem(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
        this.createdAt = System.currentTimeMillis();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public long getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}