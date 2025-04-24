package com.example.todoapp.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.model.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    
    private List<TodoItem> todoItems = new ArrayList<>();
    private OnItemClickListener listener;
    
    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        TodoItem currentItem = todoItems.get(position);
        
        holder.textViewTitle.setText(currentItem.getTitle());
        holder.textViewDescription.setText(currentItem.getDescription());
        holder.checkBoxCompleted.setChecked(currentItem.isCompleted());
        
        // Применяем зачеркивание текста, если задача выполнена
        if (currentItem.isCompleted()) {
            holder.textViewTitle.setPaintFlags(holder.textViewTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.textViewTitle.setPaintFlags(holder.textViewTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        
        // Слушатель для чекбокса
        holder.checkBoxCompleted.setOnClickListener(v -> {
            if (listener != null) {
                currentItem.setCompleted(holder.checkBoxCompleted.isChecked());
                listener.onCheckboxClick(currentItem);
            }
        });
        
        // Слушатель для кнопки удаления
        holder.buttonDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(currentItem);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return todoItems.size();
    }
    
    public void setTodoItems(List<TodoItem> todoItems) {
        this.todoItems = todoItems;
        notifyDataSetChanged();
    }
    
    public TodoItem getTodoItemAt(int position) {
        return todoItems.get(position);
    }
    
    class TodoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private CheckBox checkBoxCompleted;
        private ImageButton buttonDelete;
        
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            checkBoxCompleted = itemView.findViewById(R.id.checkbox_completed);
            buttonDelete = itemView.findViewById(R.id.button_delete);
            
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(todoItems.get(position));
                }
            });
        }
    }
    
    public interface OnItemClickListener {
        void onItemClick(TodoItem todoItem);
        void onCheckboxClick(TodoItem todoItem);
        void onDeleteClick(TodoItem todoItem);
    }
    
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}