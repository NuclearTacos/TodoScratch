package io.nucleartacos.todoscratch.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.nucleartacos.todoscratch.data.TodoItem
import io.nucleartacos.todoscratch.db

class ListItemsViewModel: ViewModel() {
    private var todoItems: LiveData<List<TodoItem>>? = null
    fun getTodoItems(listId: Int): LiveData<List<TodoItem>> {
        if (todoItems == null) {
            todoItems = db.TodoItemDao().getByListId(listId)
        }
        return todoItems!!
    }
}