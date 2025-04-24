package com.example.todoapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.adapter.TodoAdapter;
import com.example.todoapp.database.TodoRepository;
import com.example.todoapp.model.TodoItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private TodoRepository todoRepository;
    private TodoAdapter adapter;
    private List<TodoItem> todoItems;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        todoRepository = new TodoRepository(getApplication());
        
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        
        adapter = new TodoAdapter();
        recyclerView.setAdapter(adapter);
        
        todoRepository.getAllTodos().observe(this, todoItems -> {
            this.todoItems = todoItems;
            adapter.setTodoItems(todoItems);
        });
        
        FloatingActionButton buttonAddTask = findViewById(R.id.button_add_task);
        buttonAddTask.setOnClickListener(v -> showAddTaskDialog());
        
        adapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TodoItem todoItem) {
                Toast.makeText(MainActivity.this, "Нажатие на: " + todoItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
            
            @Override
            public void onCheckboxClick(TodoItem todoItem) {
                todoRepository.update(todoItem);
            }
            
            @Override
            public void onDeleteClick(TodoItem todoItem) {
                todoRepository.delete(todoItem);
                Toast.makeText(MainActivity.this, "Задача удалена", Toast.LENGTH_SHORT).show();
            }
        });
        
        // жесты смахивания для удаления
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                TodoItem todoItem = adapter.getTodoItemAt(viewHolder.getAdapterPosition());
                todoRepository.delete(todoItem);
                Toast.makeText(MainActivity.this, "Задача удалена", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
    
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить задачу");
        
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null);
        EditText editTextTitle = view.findViewById(R.id.edit_text_title);
        EditText editTextDescription = view.findViewById(R.id.edit_text_description);
        builder.setView(view);
        
        builder.setPositiveButton("Добавить", (dialog, which) -> {
            String title = editTextTitle.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            
            if (title.isEmpty()) {
                Toast.makeText(MainActivity.this, "Введите название задачи", Toast.LENGTH_SHORT).show();
                return;
            }
            
            TodoItem todoItem = new TodoItem(title, description);
            todoRepository.insert(todoItem);
            Toast.makeText(MainActivity.this, "Задача добавлена", Toast.LENGTH_SHORT).show();
        });
        
        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
        
        builder.create().show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all_tasks) {
            deleteAllTasks();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void deleteAllTasks() {
        new AlertDialog.Builder(this)
                .setTitle("Удалить все задачи?")
                .setMessage("Вы уверены, что хотите удалить все задачи?")
                .setPositiveButton("Да", (dialog, which) -> {
                    todoRepository.deleteAll();
                    Toast.makeText(MainActivity.this, "Все задачи удалены", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Нет", null)
                .show();
    }
}
